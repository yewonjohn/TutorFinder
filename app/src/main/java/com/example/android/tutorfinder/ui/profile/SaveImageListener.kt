package com.example.android.tutorfinder.ui.profile

import android.graphics.Bitmap
import androidx.lifecycle.LiveData

interface SaveImageListener {

    //to intitiate image upload
    fun onClickStart()

    //when auth is successful
    fun onUploadSuccess(response: String)

    //for failure
    fun onUploadFailiure(message:String)
}