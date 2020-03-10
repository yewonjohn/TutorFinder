package com.example.android.tutorfinder

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.parse.*

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        ParseAnalytics.trackAppOpenedInBackground(intent)




        //var ImageView = findViewById<ImageView>(R.id.imageView)
        //ImageView.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.study_background2))

        //initiating intent to TutorList activity
        val FindTutorButton = findViewById<Button>(R.id.FindTutorsButton)
        FindTutorButton.setOnClickListener{
            val intent = Intent(this, Tutors::class.java)
            startActivity(intent)
        }
        //initiating intent to Login Activity
        val LoginButton = findViewById<Button>(R.id.LoginButton)
        LoginButton.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        //initiating intent to Register Activity
        val RegisterButton = findViewById<Button>(R.id.RegisterButton)
        RegisterButton.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    //initiating intent to TutorList activity

    fun findTutors(view: View){
    }

    fun registerTutor(view: View){
    }

    fun logInAsTutor(view: View){

    }


}
