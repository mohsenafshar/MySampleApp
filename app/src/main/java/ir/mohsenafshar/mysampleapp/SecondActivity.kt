package ir.mohsenafshar.mysampleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import ir.mohsenafshar.mysampleapp.data.model.Command
import ir.mohsenafshar.mysampleapp.data.model.DataResult
import ir.mohsenafshar.mysampleapp.data.model.Response
import ir.mohsenafshar.mysampleapp.data.model.User
import ir.mohsenafshar.mysampleapp.data.remote.network.UserApi
import ir.mohsenafshar.mysampleapp.network.ApiSuccessResponse
import ir.mohsenafshar.mysampleapp.network.LiveDataCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.IOException

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
            val body = processResponse(it as ApiSuccessResponse<Response>)
            Timber.d(body.toString())
            result = body.data
            val dataResult = getResultAsObject(DataResult::class.java)
            Timber.d("${dataResult?.user?.name} ${dataResult?.user?.phone}")
        })

//        data.addSource(userInfo) {
//            data.value = (it as ApiSuccessResponse).body.data
//        }
//
//        command.addSource(userInfo) {
//            command.value = (it as ApiSuccessResponse).body.command
//        }

    }

    internal var result: Map<String, Any>? = null

    protected open fun processResponse(response: ApiSuccessResponse<Response>) = response.body


    fun <T> getResultAsObject(cls: Class<T>): T? {
        try {
            return GsonBuilder().create().fromJson(GsonBuilder().create().toJson(result), cls)//.parse(LoganSquare.serialize(getResult()), cls)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
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
