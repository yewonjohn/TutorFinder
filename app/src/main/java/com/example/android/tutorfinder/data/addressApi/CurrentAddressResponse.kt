package com.example.android.tutorfinder.data.addressApi


data class CurrentAddressResponse(
    val results: List<Result>,
    val status: String
)