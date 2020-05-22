package com.example.android.tutorfinder.ui.profile

import android.graphics.Bitmap
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.android.tutorfinder.data.repository.ProfileRepository
import com.example.android.tutorfinder.util.Coroutines
import com.parse.ParseFile
import com.parse.ParseUser

class TutorProfileViewModel: ViewModel() {

    var name:String? = null
    var age: String? = null
    var email: String? = null
    var zipcode: String? = null
    var phoneNumber: String? = null
    var subjects: String? = null
    var degree: String? = null
    var school: String? = null
    var gradDate: String? = null
    var price: String? = null
    var description: String? = null
    var image: Bitmap? = null
    var ProfileListener: ProfileListener? = null
    var GetProfileListener: GetProfileListener? = null
    var GetImageListener: GetImageListener? = null
    var saveImageListener: SaveImageListener? = null


    fun uploadImage(view: View){
        Log.i("uploadImage","triggered")
        saveImageListener?.onClickStart()
    }
    fun saveImageToUser(file: ParseFile){
        val result = ProfileRepository().saveUserImage(file)


        saveImageListener?.onUploadSuccess("Saved Image!")
        saveImageListener?.onUploadFailiure("Failed to save Image")
    }
//    fun saveInfo(view: View) {
//        ProfileListener?.onStarted()
//        //HAVE TO FIGURE OUT LOGIC FOR SUBJECTS FOR FAILIURE CONDITIONS
//
//        val profileInfo = ProfileRepository().saveProfileInfo(name!!,age!!,email!!,zipcode!!,phoneNumber!!,subjects!!,degree!!,school!!,gradDate!!,price!!,description!!)
//        ProfileListener?.onSuccess(profileInfo)
//
//    }
    fun saveInfo(view: View){
    Log.i("nameeeee",name.toString())
    Log.i("ageee",age.toString())
}
    fun getInfo():LiveData<ParseUser>{
        val userInfo = ProfileRepository().getCurrentUserInfo()
        name = userInfo.value?.get("name").toString()

        return userInfo
    }

    //USING COROUTINE! SUSPEND FUNCTIONS
    fun getProfileImage(){
        Log.i("getProfile","Called")
        GetImageListener?.onGetImgStarted()

        Coroutines.main {
            val profileImg = ProfileRepository().getProfilePhoto()
            if(profileImg === null){
                GetImageListener?.onGetImgFailiure("failed to fetch profile picture")
            }
            GetImageListener?.onGetImgSuccess(profileImg)
        }
    }
}