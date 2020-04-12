package com.example.android.tutorfinder.api


import com.google.gson.annotations.SerializedName

data class Viewport(
    val northeast: NortheastX,
    val southwest: SouthwestX
)