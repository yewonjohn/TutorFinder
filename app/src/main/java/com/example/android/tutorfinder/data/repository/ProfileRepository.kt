package com.example.android.tutorfinder.data.repository

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.tutorfinder.data.api.CurrentAddressResponse
import com.example.android.tutorfinder.data.api.JsonPlaceHolderApi
import com.example.android.tutorfinder.ui.profile.RegisterPage2Activity
import com.parse.ParseUser
import com.parse.SaveCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileRepository {

    fun fetchFormattedAddress(zipcode:String): String {
        Log.i("API FUNCTION","CALLED")
        var result:String = ""
        val retrofit = Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val jsonPlaceHolderApi: JsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)
        val call = jsonPlaceHolderApi.getAddress(zipcode,"AIzaSyBlXzJreSsIzhWffbBUlhEcP_Eoc8qIXbM")
        call.enqueue(object: Callback<CurrentAddressResponse> {
            override fun onFailure(call: Call<CurrentAddressResponse>, t: Throwable) {
                Log.i("failed api call using retrofit:", t.toString())
            }
            override fun onResponse(
                call: Call<CurrentAddressResponse>,
                response: Response<CurrentAddressResponse>
            ) {
                if (!response.isSuccessful) {
                    Log.i("Code:", response.code().toString())
                    return;
                }
                Log.i("response is success..","its successful...?")
                var addresses = response.body()?.results
                if (addresses != null) {
                    Log.i("address","is not null")
                    for (address in addresses) {
                        result = address.formattedAddress
                    }
                }
            }
        })
        Log.i("address",result)
        return result
    }

    fun saveUserDataPage1(fullname:String,email:String,phoneNumber:String,zipcode:String) :LiveData<String>{

        val saveDataResponseP1 = MutableLiveData<String>()

        var currentUser = ParseUser.getCurrentUser()
        currentUser.put("name",fullname)
        currentUser.put("email",email)
        currentUser.put("zipcode",zipcode)
        //make condition to check if string is not empty if neccessary
        currentUser.put("phoneNumber", phoneNumber.toLong())

        val formattedAdress = fetchFormattedAddress(zipcode)
        Log.i("address",formattedAdress)
        currentUser.put("address",formattedAdress)


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