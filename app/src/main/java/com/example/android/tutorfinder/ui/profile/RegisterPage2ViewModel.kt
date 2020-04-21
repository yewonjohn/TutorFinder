package com.example.android.tutorfinder.ui.profile

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.android.tutorfinder.data.repository.ProfileRepository

class RegisterPage2ViewModel : ViewModel() {

    var subjects: String? = null
    var degree: String? = null
    var school: String? = null
    var gradDate: String? = null
    var profileListener: profileListener? = null

    fun saveInfoAndProceed(view: View){
        profileListener?.onStarted()

        //val registerPage1 = ProfileRepository().saveUserDataPage1(fullname!!,email!!,phoneNumber!!,zipCode!!)
        //profileListener?.onSuccess(registerPage1)

    }}