package com.example.android.tutorfinder.ui.profile

import androidx.lifecycle.LiveData

interface RegisterPageListener {

    //for progressbar
    fun onStarted()

    //when auth is successful
    fun onSuccess(response: LiveData<String>)

    //for failure
    fun onFailiure(message:String)
}