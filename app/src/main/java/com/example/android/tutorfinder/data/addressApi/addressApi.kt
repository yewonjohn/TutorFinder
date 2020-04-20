package com.example.android.tutorfinder.data.addressApi

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val API_KEY = "AIzaSyBlXzJreSsIzhWffbBUlhEcP_Eoc8qIXbM"

interface addressApi {

    @GET("maps/api/geocode/json")
    fun getAddress(
        @Query("address") address: String,
        @Query("key") key: String
    ): Call<CurrentAddressResponse>

    companion object{
        operator fun invoke(): addressApi{
            return Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(addressApi::class.java)

        }
    }
}