package com.example.android.tutorfinder.auth

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.example.android.tutorfinder.TutorProfile
import com.parse.LogInCallback
import com.parse.ParseUser

class AuthViewModel: ViewModel() {

    var username: String? = null
    var password: String? = null
    var authListener:AuthListener? = null


    fun onLoginButtonClick(view: View){
        authListener?.onStarted()
        if(username.isNullOrEmpty() || password.isNullOrEmpty()){
            authListener?.onFailiure("invalid username or pw")
            return
        }
        authListener?.onSuccess()
    }
}