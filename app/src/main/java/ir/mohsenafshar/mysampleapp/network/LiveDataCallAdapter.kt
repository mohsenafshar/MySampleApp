package ir.mohsenafshar.mysampleapp.network

import androidx.lifecycle.LiveData
import ir.mohsenafshar.mysampleapp.data.model.MyResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapter(private val responseType: Type, private val dataType: Type) :
    CallAdapter<MyResponse, LiveData<ApiResponse<MyResponse>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<MyResponse>): LiveData<ApiResponse<MyResponse>> {
        return object : LiveData<ApiResponse<MyResponse>>() {
            private var started = AtomicBoolean(false)
            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<MyResponse> {
                        override fun onResponse(call: Call<MyResponse>, response: Response<MyResponse>) {
                            postValue(ApiResponse.create(response, dataType))
                        }

                        override fun onFailure(call: Call<MyResponse>, throwable: Throwable) {
                            postValue(ApiResponse.create(throwable))
                        }
                    })
                }
            }
        }
    }
}
