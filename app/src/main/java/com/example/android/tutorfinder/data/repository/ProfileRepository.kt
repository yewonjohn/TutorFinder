package com.example.android.tutorfinder.data.repository

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.tutorfinder.ui.profile.RegisterPage2Activity
import com.parse.ParseUser
import com.parse.SaveCallback

class ProfileRepository {


    fun saveUserDataPage1(fullname:String,email:String,phoneNumber:String,zipcode:String) :LiveData<String>{

        val saveDataResponseP1 = MutableLiveData<String>()

        var currentUser = ParseUser.getCurrentUser()
        currentUser.put("name",fullname)
        currentUser.put("email",email)
        currentUser.put("zipcode",zipcode)
        //make condition to check if string is not empty if neccessary
        currentUser.put("phoneNumber", phoneNumber.toInt())
        val asdf = phoneNumber.toInt()

        currentUser.saveInBackground(SaveCallback { e -> Unit
            if(e === null){
                saveDataResponseP1.value = "success"
            } else {
                saveDataResponseP1.value = "failed saving data"
                e.printStackTrace()
            }
        })

        return saveDataResponseP1
    }

    fun saveUserDataPage2(){

    }
}