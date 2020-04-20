package com.example.android.tutorfinder.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.parse.LogInCallback
import com.parse.ParseUser

class AuthRepository {

    fun userLogin(username: String, password: String): LiveData<String> {
        val loginResponse = MutableLiveData<String>()

        ParseUser.logInInBackground(username, password,
            LogInCallback { user, e ->
                Unit
                //on success
                if (e === null) {
                    Log.i("login", "success!")
                    loginResponse.value = "login success!"
                } else {
                    //on failiure
                    Log.i("login Failed", e.printStackTrace().toString())
                    loginResponse.value = e.toString()
                }
            })
        return loginResponse
    }

    fun userRegister(username: String, password: String): LiveData<String> {
        val registerResponse = MutableLiveData<String>()

        var user = ParseUser()
        user.username = username
        user.setPassword(password)
        user.signUpInBackground { e ->
            Unit
            if (e === null) {
                Log.i("Sign-Up", "is a success!")
                //LOG THE USER IN AFTER REGISTER
                userLogin(username, password)
                registerResponse.value = "register success!"
            }
        }
        return registerResponse
    }
}

