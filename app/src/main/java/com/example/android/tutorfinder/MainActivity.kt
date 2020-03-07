package com.example.android.tutorfinder

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var ImageView = findViewById<ImageView>(R.id.imageView)
        ImageView.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.study_background2))

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
