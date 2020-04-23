package com.example.android.tutorfinder.ui.profile

import android.graphics.Bitmap
import android.view.View
import android.widget.Spinner
import androidx.lifecycle.ViewModel
import com.example.android.tutorfinder.data.repository.ProfileRepository


class RegisterPage3ViewModel : ViewModel(){

    var description: String? = null
    var age: Spinner? = null
    var price: Spinner? = null
    var image: Bitmap? = null
    var RegisterPageListener: RegisterPageListener? = null

    fun saveInfoAndProceed(view: View){
        RegisterPageListener?.onStarted()
        //HAVE TO FIGURE OUT LOGIC FOR SUBJECTS FOR FAILIURE CONDITIONS

        val registerPage3 = ProfileRepository().saveUserDataPage3(description!!,age!!,price!!,image!!)
        RegisterPageListener?.onSuccess(registerPage3)

    }
//function for image upload
    fun uploadImage(view: View){

    }

}