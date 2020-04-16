package com.example.android.tutorfinder.auth

import androidx.lifecycle.LiveData

interface AuthListener {

    //for progressbar
    fun onStarted()

    //when auth is successful
    fun onSuccess(response: LiveData<String>)

    //for failure
    fun onFailiure(message:String)


}