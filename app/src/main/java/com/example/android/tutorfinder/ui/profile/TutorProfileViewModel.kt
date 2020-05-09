package com.example.android.tutorfinder.ui.profile

import android.graphics.Bitmap
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.android.tutorfinder.data.repository.ProfileRepository
import com.example.android.tutorfinder.util.Coroutines

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

    fun uploadImage(view: View){

    }

    fun saveInfo(view: View) {
        ProfileListener?.onStarted()
        //HAVE TO FIGURE OUT LOGIC FOR SUBJECTS FOR FAILIURE CONDITIONS

        val profileInfo = ProfileRepository().saveProfileInfo(name!!,age!!,email!!,zipcode!!,phoneNumber!!,subjects!!,degree!!,school!!,gradDate!!,price!!,description!!)
        ProfileListener?.onSuccess(profileInfo)
        //this triggers.. if uncommented
//        if (profileInfo != null) {
//            GetProfileListener?.onGETSuccess(profileInfo)
//        }
    }
    fun getInfo(){
        Log.i("getInfo","Called")
        GetProfileListener?.onGETStarted()

        //have to figure out failiure conditions

        val userInfo = ProfileRepository().getCurrentUserInfo()

        if(userInfo.value.toString().isEmpty()){
            GetProfileListener?.onGETFailiure("Failed to fetch user data")
            return
        }

        Log.i("userInfo In ViewModel",userInfo.value?.get("name").toString())
        GetProfileListener?.onGETSuccess(userInfo)
        Log.i("Test","testingg")
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