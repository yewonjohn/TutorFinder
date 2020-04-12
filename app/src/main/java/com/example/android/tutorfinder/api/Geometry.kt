package com.example.android.tutorfinder.api


import com.google.gson.annotations.SerializedName

data class Geometry(
    val bounds: Bounds,
    val location: Location,
    @SerializedName("location_type")
    val locationType: String,
    val viewport: Viewport
)