package com.example.android.tutorfinder.ui.profile

import androidx.lifecycle.LiveData
import com.parse.ParseUser

interface ProfileListener {

        //for progressbar
        fun onStarted()

        //when auth is successful
        fun onSuccess(response: LiveData<ParseUser>?)

        //for failure
        fun onFailiure(message:String)
}