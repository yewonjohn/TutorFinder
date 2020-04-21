package com.example.android.tutorfinder.ui.profile

import androidx.lifecycle.LiveData
import com.parse.ParseUser

interface GetProfileListener {
    //for progressbar
    fun onGETStarted()

    //when auth is successful
    fun onGETSuccess(response: LiveData<ParseUser>)

    //for failure
    fun onGETFailiure(message:String)
}