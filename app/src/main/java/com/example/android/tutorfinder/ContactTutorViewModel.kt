package com.example.android.tutorfinder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.tutorfinder.data.repository.ProfileRepository
import com.parse.Parse
import com.parse.ParseUser

class ContactTutorViewModel : ViewModel() {

    var email: String? = null
    var phoneNumber: String? = null
    var repository = ProfileRepository()

//    fun getUserInfo(): LiveData<String>{
//        var currentUser = repository.getCurrentUserInfo()
//
//        email = currentUser.value?.email
//        phoneNumber = currentUser.value?.getString("phoneNumber")
//
//        var emailLive = MutableLiveData<String>()
//        emailLive.value = email
//        return emailLive
//    }


    // TODO: Implement the ViewModel
}
