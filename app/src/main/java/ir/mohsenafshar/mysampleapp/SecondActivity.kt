package ir.mohsenafshar.mysampleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.gson.GsonBuilder
import ir.mohsenafshar.mysampleapp.data.model.Command
import ir.mohsenafshar.mysampleapp.data.model.DataResult
import ir.mohsenafshar.mysampleapp.data.model.MyResponse
import ir.mohsenafshar.mysampleapp.data.remote.network.UserApi
import ir.mohsenafshar.mysampleapp.network.ApiResponse
import ir.mohsenafshar.mysampleapp.network.ApiSuccessResponse
import ir.mohsenafshar.mysampleapp.network.LiveDataCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.IOException
import java.lang.reflect.*
import java.lang.reflect.Array

class SecondActivity : AppCompatActivity() {

    private val BASE_URL = "http://mohsenafshar.ir/"

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private val data = MediatorLiveData<String>()
    private val command = MediatorLiveData<Command>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)


        val userInfo = provideUserApi().getUserInfo()
        userInfo.observe(this, Observer {
            val body = processResponse(it as ApiSuccessResponse)
            Timber.d(body.toString())
            result = body.data

            val resultAsObject = getResultAsObject<DataResult>(it)
            //val dataResult = getResultAsObject(DataResult::class.java)
            //Timber.d("${dataResult?.user?.name} ${dataResult?.user?.phone}")
        })

//        data.addSource(userInfo) {
//            data.value = (it as ApiSuccessResponse).body.data
//        }
//
//        command.addSource(userInfo) {
//            command.value = (it as ApiSuccessResponse).body.command
//        }

    }

    fun <T> getResultAsObject(apiResponse: ApiSuccessResponse): T? {
        try {
            return GsonBuilder().create().fromJson(
                GsonBuilder().create().toJson(processResponse(apiResponse).data),
                apiResponse.dataType
            )
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }

    internal var result: Map<String, Any>? = null

    protected open fun processResponse(response: ApiSuccessResponse) = response.body

    class DataHandler<T>(
        private val apiResponse: ApiSuccessResponse,
        private val resultLD: LiveData<ApiResponse<MyResponse>>
    ) {

        private val data = MediatorLiveData<T>()
        private val command = MediatorLiveData<Command>()

        fun processResponse(response: ApiSuccessResponse) = response.body

        fun getResultData() {
            data.addSource(resultLD) {
               // data.value = getResultAsObject(DataResult::class.java)
            }
        }

        val bodyType = getParameterUpperBound(0, apiResponse as ParameterizedType)

        fun <T> getResultAsObject(type: Type): T? {
            try {
                return GsonBuilder().create().fromJson(
                    GsonBuilder().create().toJson(processResponse(apiResponse).data),
                    type
                )
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }
        }

        fun <T> getResultAsObject(cls: Class<T>): T? {
            try {
                return GsonBuilder().create()
                    .fromJson(GsonBuilder().create().toJson(processResponse(apiResponse).data), cls)
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }
        }

        fun getParameterUpperBound(index: Int, type: ParameterizedType): Type {
            val types = type.actualTypeArguments
            require(!(index < 0 || index >= types.size)) { "Index " + index + " not in range [0," + types.size + ") for " + type }
            val paramType = types[index]
            return if (paramType is WildcardType) {
                paramType.upperBounds[0]
            } else paramType
        }

        fun getRawType(type: Type): Class<*> {
            //checkNotNull(type, "type == null")

            if (type is Class<*>) {
                // Type is a normal class.
                return type as Class<*>
            }
            if (type is ParameterizedType) {
                val parameterizedType = type as ParameterizedType

                // I'm not exactly sure why getRawType() returns Type instead of Class. Neal isn't either but
                // suspects some pathological case related to nested classes exists.
                val rawType = parameterizedType.rawType
                if (rawType !is Class<*>) throw IllegalArgumentException()
                return rawType as Class<*>
            }
            if (type is GenericArrayType) {
                val componentType = (type as GenericArrayType).genericComponentType
                return Array.newInstance(getRawType(componentType), 0).javaClass
            }
            if (type is TypeVariable<*>) {
                // We could use the variable's bounds, but that won't work if there are multiple. Having a raw
                // type that's more general than necessary is okay.
                return Any::class.java
            }
            if (type is WildcardType) {
                return getRawType((type as WildcardType).upperBounds[0])
            }

            throw IllegalArgumentException(
                "Expected a Class, ParameterizedType, or "
                        + "GenericArrayType, but <" + type + "> is of type " + type.javaClass.name
            )
        }

    }

    fun provideUserApi(): UserApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addNetworkInterceptor(StethoInterceptor())
                    .build()
            )
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }
}
