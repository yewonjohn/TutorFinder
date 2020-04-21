package com.example.android.tutorfinder.ui.profile

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.parse.ParseUser

interface GetImageListener {
    //for progressbar
    fun onGetImgStarted()

    //when auth is successful
    fun onGetImgSuccess(response: Bitmap?)

    //for failure
    fun onGetImgFailiure(message:String)
}