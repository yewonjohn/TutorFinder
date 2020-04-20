package com.example.android.tutorfinder.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.tutorfinder.data.api.CurrentAddressResponse
import com.example.android.tutorfinder.data.api.JsonPlaceHolderApi
import com.parse.LogInCallback
import com.parse.ParseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

