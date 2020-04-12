package com.example.android.tutorfinder.api


import com.google.gson.annotations.SerializedName

data class Bounds(
    val northeast: Northeast,
    val southwest: Southwest
)