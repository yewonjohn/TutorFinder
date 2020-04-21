package com.example.android.tutorfinder.ui.profile

import android.graphics.Bitmap
import android.view.View
import android.widget.Spinner
import androidx.lifecycle.ViewModel



class RegisterPage3ViewModel : ViewModel() {

    var description: String? = null
    var age: Spinner? = null
    var price: Spinner? = null
    var image: Bitmap? = null
    var RegisterPageListener: RegisterPageListener? = null

    fun saveInfoAndProceed(view: View){
        RegisterPageListener?.onStarted()
        //HAVE TO FIGURE OUT LOGIC FOR SUBJECTS FOR FAILIURE CONDITIONS

//        val registerPage2 = ProfileRepository().saveUserDataPage2(subjects!!,degree!!,school!!,gradDate!!)
//        profileListener?.onSuccess(registerPage2)

    }

    fun uploadImage(view: View){

    }

}