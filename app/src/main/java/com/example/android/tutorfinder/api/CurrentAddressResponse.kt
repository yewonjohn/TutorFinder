package com.example.android.tutorfinder.api


import com.google.gson.annotations.SerializedName

data class CurrentAddressResponse(
    val results: List<Result>,
    val status: String
)