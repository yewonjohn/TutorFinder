package com.example.android.tutorfinder.data.addressApi

import com.google.gson.annotations.SerializedName

class FullAddress {

    @SerializedName("results")
    private var address:String = ""

    private var status:String = ""

    fun getAddress(): String {
        return address
    }

    fun getStatus(): String {
        return status
    }


}