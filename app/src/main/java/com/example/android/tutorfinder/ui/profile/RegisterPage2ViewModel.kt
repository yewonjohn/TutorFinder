package com.example.android.tutorfinder.ui.profile

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.android.tutorfinder.data.repository.ProfileRepository

class RegisterPage2ViewModel : ViewModel() {

    var subjects: String? = null
    var degree: String? = null
    var school: String? = null
    var gradDate: String? = null
    var RegisterPageListener: RegisterPageListener? = null

    fun saveInfoAndProceed(view: View){
        RegisterPageListener?.onStarted()
        //HAVE TO FIGURE OUT LOGIC FOR SUBJECTS FOR FAILIURE CONDITIONS

        val registerPage2 = ProfileRepository().saveUserDataPage2(subjects!!,degree!!,school!!,gradDate!!)
        RegisterPageListener?.onSuccess(registerPage2)

    }}