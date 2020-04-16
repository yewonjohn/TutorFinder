package com.example.android.tutorfinder.auth

interface AuthListener {

    //for progressbar
    fun onStarted()

    //when auth is successful
    fun onSuccess()

    //for failure
    fun onFailiure(message:String)


}