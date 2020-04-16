package com.example.android.tutorfinder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.android.tutorfinder.data.api.CurrentAddressResponse
import com.example.android.tutorfinder.data.api.JsonPlaceHolderApi
import com.example.android.tutorfinder.auth.LoginActivity
import com.parse.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //declaring all the buttons
        var loginButton: Button = findViewById<Button>(R.id.LoginButton)
        var findTutorButton: Button = findViewById<Button>(R.id.FindTutorsButton)
        var registerButton: Button = findViewById<Button>(R.id.RegisterButton)
        var myProfileButton: Button = findViewById<Button>(R.id.myProfileButton)


        //THIS CONDITION WILL NOT WORK IF AUTOMATIC USERS ARE ENABLED
        //if(ParseUser.getCurrentUser() !== null)


        ParseAnalytics.trackAppOpenedInBackground(intent)

        //if user is logged in, login & registration buttons are hidden. MyProfile Button becomes visible
        if(ParseUser.getCurrentUser() !== null){

            loginButton.visibility = View.GONE
            registerButton.visibility = View.GONE
            myProfileButton.visibility = View.VISIBLE
        }

        //initiating intent to TutorProfile activity
        myProfileButton.setOnClickListener{
            val intent = Intent(this, TutorProfile::class.java)
            startActivity(intent)
        }
        //initiating intent to TutorList activity
        findTutorButton.setOnClickListener{
            val intent = Intent(this, Tutors::class.java)
            startActivity(intent)
        }
        //initiating intent to Login Activity
        loginButton.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        //initiating intent to Register Activity
        registerButton.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


        // TESTING OUT RETROFIT HERE ----------------------v
        var result = ""
        val retrofit = Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val jsonPlaceHolderApi: JsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)
        val call = jsonPlaceHolderApi.getAddress("07661","AIzaSyBlXzJreSsIzhWffbBUlhEcP_Eoc8qIXbM")
        call.enqueue(object: Callback<CurrentAddressResponse>{
            override fun onFailure(call: Call<CurrentAddressResponse>, t: Throwable) {
                Log.i("failed retrofit thingy:",t.toString())
            }

            override fun onResponse(call: Call<CurrentAddressResponse>, response: Response<CurrentAddressResponse>) {
                if(!response.isSuccessful){
                    Log.i("Code:",response.code().toString())
                    return;
                }

                var addresses = response.body()
                var addresses1 = addresses?.results

                if (addresses1 != null) {
                    for(address in addresses1){
                        result = address.formattedAddress
                        Log.i("result",result)
                    }
                }

            }
        })
    }
}
