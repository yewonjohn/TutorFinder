package com.example.android.tutorfinder.auth

import android.view.View
import com.example.android.tutorfinder.data.repository.UserRepository
import kotlinx.android.synthetic.main.activity_register.*

class RegisterViewModel {

    var email: String? = null
    var username: String? = null
    var password: String? = null
    var passwordConfirm: String? = null
    var authListener:AuthListener? = null


    fun onRegisterButtonClicked(view: View){
        authListener?.onStarted()
        if(username.isNullOrEmpty() || password.isNullOrEmpty() || email.isNullOrEmpty() || passwordConfirm.isNullOrEmpty()){
            authListener?.onFailiure("invalid username or pw")
            return
        }
        if(!password.equals(passwordConfirm)){
            authListener?.onFailiure("password does not match")
            return
        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            authListener?.onFailiure("email is invalid")
        }
//        val loginResponse = UserRepository().userLogin(username!!,password!!)
//        authListener?.onSuccess(loginResponse)
    }
}