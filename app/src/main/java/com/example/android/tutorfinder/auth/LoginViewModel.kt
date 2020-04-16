package com.example.android.tutorfinder.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.android.tutorfinder.data.repository.UserRepository

class LoginViewModel: ViewModel() {

    var username: String? = null
    var password: String? = null
    var authListener:AuthListener? = null


    fun onLoginButtonClick(view: View){
        authListener?.onStarted()
        if(username.isNullOrEmpty() || password.isNullOrEmpty()){
            authListener?.onFailiure("invalid username or pw")
            return
        }
        val loginResponse = UserRepository().userLogin(username!!,password!!)
        authListener?.onSuccess(loginResponse)
    }
}