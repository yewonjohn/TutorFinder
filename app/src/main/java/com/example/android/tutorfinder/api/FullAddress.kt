package com.example.android.tutorfinder.api

import com.google.gson.annotations.SerializedName

class FullAddress {

    @SerializedName("formatted_address")
    private var address:String = ""

    fun getAddress(): String {
        return address;
    }


}