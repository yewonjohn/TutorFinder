package com.example.android.tutorfinder.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val API_KEY = "AIzaSyBlXzJreSsIzhWffbBUlhEcP_Eoc8qIXbM"

public interface JsonPlaceHolderApi {

    //https://maps.googleapis.com/maps/api/geocode/json?address=07661&key=AIzaSyBlXzJreSsIzhWffbBUlhEcP_Eoc8qIXbM

    @GET("maps/api/geocode/json")
    fun getAddress(
        @Query("address") address: String,
        @Query("key") key: String
    ): Call<CurrentAddressResponse>

//    companion object {
//        operator fun invoke(): JsonPlaceHolderApi{
//            val requestInterceptor = Interceptor {chain ->
//
//                val url = chain.request()
//                    .url()
//                    .newBuilder()
//                    .addQueryParameter("key", API_KEY)
//                    .build()
//
//                val request = chain.request()
//                    .newBuilder()
//                    .url(url)
//                    .build()
//
//                return@Interceptor chain.proceed(request)
//
//            }
//            val okHttpClient = OkHttpClient.Builder()
//                .addInterceptor(requestInterceptor)
//                .build()
//
//            return Retrofit.Builder()
//                .client(okHttpClient)
//                .baseUrl("https://maps.googleapis.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(JsonPlaceHolderApi::class.java)
//        }
//    }
}