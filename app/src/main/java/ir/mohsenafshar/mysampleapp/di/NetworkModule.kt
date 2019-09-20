package ir.mohsenafshar.mysampleapp.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import ir.mohsenafshar.mysampleapp.data.remote.network.WaybillApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import javax.inject.Singleton

@Module
class NetworkModule {

    private val BASE_URL = "https://api.spotify.com/v1/"

    @Singleton
    @Provides
    fun provideSearchApi(): WaybillApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addNetworkInterceptor(StethoInterceptor())
                    .build()
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WaybillApi::class.java)
    }
}
