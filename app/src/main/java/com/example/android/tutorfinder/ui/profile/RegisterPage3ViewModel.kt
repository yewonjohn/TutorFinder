package com.example.android.tutorfinder.ui.profile

import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.widget.Spinner
import androidx.databinding.InverseBindingMethod
import androidx.databinding.InverseBindingMethods
import androidx.lifecycle.ViewModel
import com.example.android.tutorfinder.data.repository.ProfileRepository


class RegisterPage3ViewModel : ViewModel(){

    var description: String? = null
    var agePosition: Int? = null
    var pricePosition: Int? = null
    //var image: Bitmap? = null
    // add img paramters to both viewmodel and repository
    var RegisterPageListener: RegisterPageListener? = null


    fun saveInfoAndProceed(view: View){
        val age: Int? = agePosition?.plus(1)
        val price: Int? = pricePosition?.plus(1)
        Log.i("age",age.toString())
        Log.i("price",price.toString())
        RegisterPageListener?.onStarted()
        //HAVE TO FIGURE OUT LOGIC FOR SUBJECTS FOR FAILIURE CONDITIONS

        val registerPage3 = ProfileRepository().saveUserDataPage3(description!!,age!!,price!!)
        RegisterPageListener?.onSuccess(registerPage3)

    }
//function for image upload
    fun uploadImage(view: View){

    }

}