package com.example.android.tutorfinder.data.repository

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.tutorfinder.TutorProfile
import com.parse.LogInCallback
import com.parse.ParseUser

class UserRepository {

    fun userLogin(username:String, password:String): LiveData<String>{

        val loginResponse = MutableLiveData<String>()

            ParseUser.logInInBackground(username,password,
                LogInCallback { user, e ->  Unit
                //on success
                if(e === null){
                    Log.i("login","success!")
//                    val intent = Intent(this, TutorProfile::class.java)
//                    startActivity(intent)
                    loginResponse.value = "login success!"
                } else{
                //on failiure
                    Log.i("login Failed",e.printStackTrace().toString())
                    loginResponse.value = e.toString()
                }
                })
        return loginResponse
        }

    }

}