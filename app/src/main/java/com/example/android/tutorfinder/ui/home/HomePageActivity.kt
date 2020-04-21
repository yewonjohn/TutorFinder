package com.example.android.tutorfinder.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.android.tutorfinder.R
import com.example.android.tutorfinder.ui.profile.TutorProfileActivity
import com.example.android.tutorfinder.Tutors
import com.example.android.tutorfinder.ui.auth.LoginActivity
import com.example.android.tutorfinder.ui.auth.RegisterActivity
import com.parse.*

class HomePageActivity : AppCompatActivity() {

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
            val intent = Intent(this, TutorProfileActivity::class.java)
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
    }
}
