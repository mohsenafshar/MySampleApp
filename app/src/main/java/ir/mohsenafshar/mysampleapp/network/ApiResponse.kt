package ir.mohsenafshar.mysampleapp.network

import ir.mohsenafshar.mysampleapp.data.model.MyResponse
import retrofit2.Response
import timber.log.Timber
import java.lang.reflect.Type
import java.util.regex.Pattern

sealed class ApiResponse<T> {
    companion object {
        fun create(error: Throwable): ApiErrorResponse {
            return ApiErrorResponse(error.message ?: "unknown error")
        }

        fun create(response: Response<MyResponse>, dataType: Type): ApiResponse<MyResponse> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(
                        body = body,
                        dataType = dataType,
                        linkHeader = response.headers()?.get("link")
                    )
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                ApiErrorResponse(errorMsg ?: "unknown error")
            }
        }
    }
}

/**
 * separate class for HTTP 204 responses so that we can make ApiSuccessResponse's body non-null.
 */
class ApiEmptyResponse : ApiResponse<MyResponse>()

data class ApiSuccessResponse(
    val body: MyResponse,
    val dataType: Type,
    val links: Map<String, String>
) : ApiResponse<MyResponse>() {
    constructor(body: MyResponse, dataType: Type, linkHeader: String?) : this(
        body = body,
        dataType = dataType,
        links = linkHeader?.extractLinks() ?: emptyMap()
    )

    val nextPage: Int? by lazy(LazyThreadSafetyMode.NONE) {
        links[NEXT_LINK]?.let { next ->
            val matcher = PAGE_PATTERN.matcher(next)
            if (!matcher.find() || matcher.groupCount() != 1) {
                null
            } else {
                try {
                    Integer.parseInt(matcher.group(1))
                } catch (ex: NumberFormatException) {
                    Timber.w("cannot parse next page from %s", next)
                    null
                }
            }
        }
    }

    companion object {
        private val LINK_PATTERN = Pattern.compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"")
        private val PAGE_PATTERN = Pattern.compile("\\bpage=(\\d+)")
        private const val NEXT_LINK = "next"

        private fun String.extractLinks(): Map<String, String> {
            val links = mutableMapOf<String, String>()
            val matcher = LINK_PATTERN.matcher(this)

            while (matcher.find()) {
                val count = matcher.groupCount()
                if (count == 2) {
                    links[matcher.group(2)] = matcher.group(1)
                }
            }
            return links
        }

    }
}

data class ApiErrorResponse(val errorMessage: String) : ApiResponse<MyResponse>()
