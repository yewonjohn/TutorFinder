package com.example.android.tutorfinder.data.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Spinner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.tutorfinder.data.addressApi.CurrentAddressResponse
import com.example.android.tutorfinder.data.addressApi.addressApi
import com.parse.GetDataCallback
import com.parse.ParseFile
import com.parse.ParseUser
import com.parse.SaveCallback
import kotlinx.android.synthetic.main.activity_tutor_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.InvocationTargetException
import java.util.ArrayList

class ProfileRepository {

    //uses retrofit api call
    fun fetchFormattedAddressAndSave(zipcode:String,user:ParseUser) {
        var result:String = ""

        addressApi().getAddress(zipcode,"AIzaSyBlXzJreSsIzhWffbBUlhEcP_Eoc8qIXbM")
            .enqueue(object: Callback<CurrentAddressResponse> {
            override fun onFailure(call: Call<CurrentAddressResponse>, t: Throwable) {
                Log.i("failed api call using retrofit:", t.toString())
            }
            override fun onResponse(
                call: Call<CurrentAddressResponse>,
                response: Response<CurrentAddressResponse>
            ) {
                if (!response.isSuccessful) {
                    Log.i("Code:", response.code().toString())

                }
                val addresses = response.body()?.results
                if (addresses != null) {
                    for (address in addresses) {
                        result = address.formattedAddress
                        user.put("address",result)
                        //NOT SURE IF THIS IS BEST WAY....
                        user.save()
                    }
                }
            }
        })
    }
    fun saveUserDataPage1(fullname:String,email:String,phoneNumber:String,zipcode:String) :LiveData<String>{

        var saveDataResponseP1 = MutableLiveData<String>()

        var currentUser = ParseUser.getCurrentUser()
        currentUser.put("name",fullname)
        currentUser.put("email",email)
        currentUser.put("zipcode",zipcode)
        //make condition to check if string is not empty if neccessary
        //.toLong() <-- how u handle long numbers like phone digits
        currentUser.put("phoneNumber", phoneNumber)
        //calling API & saving data. This is probably not the best idea..
        fetchFormattedAddressAndSave(zipcode,currentUser)
        currentUser.saveInBackground(SaveCallback { e -> Unit
            if(e === null){
                saveDataResponseP1.value = "success"
            } else {
                saveDataResponseP1.value = "failed saving data"
                e.printStackTrace()
            }
        })

        return saveDataResponseP1
    }

    fun saveUserDataPage2(subjects:String,degree:String,school:String,gradDate:String): LiveData<String>{

        var saveDataResponseP2 = MutableLiveData<String>()

        var currentUser = ParseUser.getCurrentUser()
        currentUser.put("subjects",subjects)
        currentUser.put("highestDegree",degree)
        currentUser.put("school",school)
        currentUser.put("graduationDate", gradDate)
        currentUser.saveInBackground(SaveCallback { e -> Unit
            if(e === null){
                saveDataResponseP2.value = "Successfully saved"
            } else {
                saveDataResponseP2.value = "Unsuccessful in saving user data"
                e.printStackTrace()
            }
        })
    return saveDataResponseP2
    }

    fun saveUserDataPage3(description:String, age:Int, price:Int): LiveData<String>{

        var saveDataResponseP3 = MutableLiveData<String>()

        var currentUser = ParseUser.getCurrentUser()
        currentUser.put("description",description)
        currentUser.put("age",age.toString())
        currentUser.put("cost",price.toString())
        //ADD IMAGE SAVING CODE HERE
        currentUser.saveInBackground(SaveCallback { e -> Unit
            if(e === null){
                saveDataResponseP3.value = "Successfully saved"
            } else {
                saveDataResponseP3.value = "Unsuccessful in saving user data"
                e.printStackTrace()
            }
        })

        return saveDataResponseP3
    }
    //saving profile THIS WORKS
    fun saveProfileInfo(name:String, age:String, email: String, zipcode: String, phoneNumber: String,
                        subjects: String, degree: String, school: String, gradDate: String, price:String,
                        description: String): LiveData<ParseUser>?{

        var saveUserResponse:MutableLiveData<ParseUser>? = MutableLiveData<ParseUser>()
        var currentUser = ParseUser.getCurrentUser()
        saveUserResponse?.value = currentUser


        //data is not saving properly here for some reason
        try {
            currentUser.put("name",name)
            currentUser.put("age",age)
            currentUser.put("email",email)
            currentUser.put("zipcode",zipcode)
            currentUser.put("phoneNumber",phoneNumber)
            currentUser.put("subjects",subjects)
            currentUser.put("highestDegree",degree)
            currentUser.put("school",school)
            currentUser.put("graduationDate",gradDate)
            currentUser.put("cost",price)
            currentUser.put("description",description)
            currentUser.put("educationDesc", "$school $degree $gradDate")
            fetchFormattedAddressAndSave(zipcode,currentUser)

            currentUser.saveInBackground(SaveCallback { e -> Unit
                if(e === null){
                    Log.i("data","successfully saved")
                }
                else {
                    saveUserResponse = null
                    Log.i("failed", "unsuccessful in saving user data")
                    Log.i("parent error",e.toString())
                    Log.i("parent error",e.cause.toString())
                }
            })
        }catch (e:InvocationTargetException){
            Log.i("error",e.cause.toString())
        }
        return saveUserResponse
    }
//method to fetch user info. CHANGING TO <ArrayList<String>> rn
    fun getCurrentUserInfo():LiveData<ArrayList<String>>{
        val currentUser = ParseUser.getCurrentUser()
//        val userResponse = MutableLiveData<ParseUser>()
//        userResponse.value = currentUser

        val response = MutableLiveData<ArrayList<String>>()
        currentUser.getString("name")?.let { response.value?.add(it) }
        currentUser.getString("age")?.let { response.value?.add(it) }
        currentUser.getString("email")?.let { response.value?.add(it) }
        currentUser.getString("zipcode")?.let { response.value?.add(it) }
        currentUser.getString("phoneNumber")?.let { response.value?.add(it) }
        currentUser.getString("subjects")?.let { response.value?.add(it) }
        currentUser.getString("highestDegree")?.let { response.value?.add(it) }
        currentUser.getString("school")?.let { response.value?.add(it) }
        currentUser.getString("graduationDate")?.let { response.value?.add(it) }
        currentUser.getString("cost")?.let { response.value?.add(it) }
        currentUser.getString("description")?.let { response.value?.add(it) }

        Log.i("asdfasdf", response.value?.toString())

        return response
    }

    fun getProfilePhoto(): Bitmap?{
        val currentUser = ParseUser.getCurrentUser()

        var file: ParseFile? = currentUser.getParseFile("image")
        //NOT USING BACKGROUND! TRY OUT SUSPEND FUNCTION FOR THIS
        val data = file?.data
        val bmp: Bitmap? = data?.size?.let { BitmapFactory.decodeByteArray(data,0, it) }

        return bmp
    }

//takes image from UI and saves to current user
    //RESULT IS BEING GIVEN BEFORE SAVEINBACKGROUND FETCHES DATA AND SETS THE NEW RESULT VALUE
    fun saveUserImage(file: ParseFile): String{
    var result = "";
    try{
        val currentUser = ParseUser.getCurrentUser()
        currentUser.put("image",file)
        currentUser.saveInBackground(SaveCallback { e -> Unit
            if(e === null){
                Log.i("saveUserImg","success")
                result = "success"
            } else {
                Log.i("saveUserImg","failed")
                result = "failed"
            }
        })
    }catch (e:Exception){
        Log.i("error",e.printStackTrace().toString())
        }
    return result
    }





}