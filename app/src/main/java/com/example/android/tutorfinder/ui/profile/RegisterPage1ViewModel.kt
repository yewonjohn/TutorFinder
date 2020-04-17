package com.example.android.tutorfinder.ui.profile

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.android.tutorfinder.data.repository.ProfileRepository

class RegisterPage1ViewModel: ViewModel() {

    var fullname: String? = null
    var phoneNumber: String? = null
    var zipCode: String? = null
    var email: String? = null
    var profileListener: profileListener? = null

    fun saveInfoAndProceed(view: View){
        profileListener?.onStarted()

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            profileListener?.onFailiure("email is invalid")
            return
        }
            val registerPage1 = ProfileRepository().saveUserDataPage1(fullname!!,email!!,phoneNumber!!,zipCode!!)
            profileListener?.onSuccess(registerPage1)
    }

}