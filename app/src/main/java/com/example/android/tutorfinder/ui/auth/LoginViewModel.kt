package com.example.android.tutorfinder.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.android.tutorfinder.data.repository.AuthRepository

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
        val loginResponse = AuthRepository().userLogin(username!!,password!!)
        authListener?.onSuccess(loginResponse)
    }
}