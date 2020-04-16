package com.example.android.tutorfinder.data.repository

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.tutorfinder.RegisterPage1Activity
import com.example.android.tutorfinder.TutorProfile
import com.parse.LogInCallback
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_register.*

class UserRepository {

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

    fun userRegister(email: String, username: String, password: String): LiveData<String> {
        val registerResponse = MutableLiveData<String>()

        var user = ParseUser()
        user.email = email
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

