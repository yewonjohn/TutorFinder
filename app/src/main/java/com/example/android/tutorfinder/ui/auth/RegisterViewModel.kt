package com.example.android.tutorfinder.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.android.tutorfinder.data.repository.AuthRepository

class RegisterViewModel: ViewModel(){

    var username: String? = null
    var password: String? = null
    var passwordConfirm: String? = null
    var authListener:AuthListener? = null


    fun onRegisterButtonClicked(view: View){
        authListener?.onStarted()
        //checking all these conditions for validity
        if(username.isNullOrEmpty() || password.isNullOrEmpty() || passwordConfirm.isNullOrEmpty()){
            authListener?.onFailiure("invalid username or pw")
            return
        }
        if(!password.equals(passwordConfirm)){
            authListener?.onFailiure("password does not match")
            return
        }
        //success
       val registerResponse = AuthRepository().userRegister(username!!,password!!)
        authListener?.onSuccess(registerResponse)
    }
}