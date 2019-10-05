package ir.mohsenafshar.mysampleapp.data.remote.network

import androidx.lifecycle.LiveData
import ir.mohsenafshar.mysampleapp.data.model.DataResult
import ir.mohsenafshar.mysampleapp.data.model.MyResponse
import ir.mohsenafshar.mysampleapp.network.ApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface UserApi {

    @GET("dataa")
    fun getUserInfo() : LiveData<ApiResponse<DataResult>>



}