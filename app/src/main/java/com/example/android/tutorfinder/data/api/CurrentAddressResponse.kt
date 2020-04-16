package com.example.android.tutorfinder.data.api


data class CurrentAddressResponse(
    val results: List<Result>,
    val status: String
)