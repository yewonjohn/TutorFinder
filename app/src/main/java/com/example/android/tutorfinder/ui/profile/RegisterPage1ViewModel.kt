package com.example.android.tutorfinder.ui.profile

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.android.tutorfinder.data.repository.ProfileRepository

class RegisterPage1ViewModel: ViewModel() {

    var fullname: String? = null
    var phoneNumber: String? = null
    var zipCode: String? = null
    var email: String? = null
    var RegisterPageListener: RegisterPageListener? = null

    fun saveInfoAndProceed(view: View){
        RegisterPageListener?.onStarted()

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            RegisterPageListener?.onFailiure("email is invalid")
            return
        }
            val registerPage1 = ProfileRepository().saveUserDataPage1(fullname!!,email!!,phoneNumber!!,zipCode!!)
            RegisterPageListener?.onSuccess(registerPage1)

    }

}