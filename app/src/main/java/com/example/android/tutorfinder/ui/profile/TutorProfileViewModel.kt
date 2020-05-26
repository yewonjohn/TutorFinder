package com.example.android.tutorfinder.ui.profile

import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.tutorfinder.data.repository.ProfileRepository
import com.example.android.tutorfinder.util.Coroutines
import com.parse.ParseFile
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_tutor_profile.*
import java.util.ArrayList

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
    fun saveInfo(view: View) {
        ProfileListener?.onStarted()
        //HAVE TO FIGURE OUT LOGIC FOR SUBJECTS FOR FAILIURE CONDITIONS

        val profileInfo = ProfileRepository().saveProfileInfo(name!!,age!!,email!!,zipcode!!,phoneNumber!!,subjects!!,degree!!,school!!,gradDate!!,price!!,description!!)
        ProfileListener?.onSuccess(profileInfo)

    }
//    fun saveInfo(view: View){
//    Log.i("nameeeee",name.toString())
//    Log.i("ageee",age.toString())
//}
    //changing to LiveDate<ArrayList<String>> for now
    fun getInfo():LiveData<ArrayList<String>>{
        val userInfo = ProfileRepository().getCurrentUserInfo()
        val userInfoList = userInfo.value

        name = userInfoList?.get(0)
        age = userInfoList?.get(1)
        email = userInfoList?.get(2)
        zipcode = userInfoList?.get(3)
        phoneNumber = userInfoList?.get(4)
        subjects = userInfoList?.get(5)
        degree = userInfoList?.get(6)
        school = userInfoList?.get(7)
        gradDate = userInfoList?.get(8)
        price = userInfoList?.get(9)
        description = userInfoList?.get(10)

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