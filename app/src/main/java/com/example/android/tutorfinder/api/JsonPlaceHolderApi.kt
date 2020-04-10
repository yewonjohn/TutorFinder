package com.example.android.tutorfinder.api

import retrofit2.Call
import retrofit2.http.GET

public interface JsonPlaceHolderApi {

    @GET("json?address=07661&key=AIzaSyBlXzJreSsIzhWffbBUlhEcP_Eoc8qIXbM")
    fun getAddress() : Call<List<FullAddress>>

}