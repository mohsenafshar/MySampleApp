package ir.mohsenafshar.mysampleapp.data.remote.network

import androidx.lifecycle.LiveData
import ir.mohsenafshar.mysampleapp.data.model.Response
import ir.mohsenafshar.mysampleapp.network.ApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface UserApi {

    @GET("data")
    fun getUserInfo() : LiveData<ApiResponse<Response>>

    fun getUsersInfo() :
            Call<Response>


}