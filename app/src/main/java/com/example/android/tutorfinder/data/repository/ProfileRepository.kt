package com.example.android.tutorfinder.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.tutorfinder.data.addressApi.CurrentAddressResponse
import com.example.android.tutorfinder.data.addressApi.addressApi
import com.parse.ParseUser
import com.parse.SaveCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileRepository {

    fun fetchFormattedAddress(zipcode:String,user:ParseUser) {
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

        val saveDataResponseP1 = MutableLiveData<String>()

        var currentUser = ParseUser.getCurrentUser()
        currentUser.put("name",fullname)
        currentUser.put("email",email)
        currentUser.put("zipcode",zipcode)
        //make condition to check if string is not empty if neccessary
        currentUser.put("phoneNumber", phoneNumber.toLong())
        //calling API & saving data. This is probably not the best idea..
        fetchFormattedAddress(zipcode,currentUser)
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

    fun saveUserDataPage2(){

    }
}