package com.example.android.tutorfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)




        val LoginButton = findViewById<Button>(R.id.loginButton)
        LoginButton.setOnClickListener{
            val intent = Intent(this, TutorProfile::class.java)
            startActivity(intent)
        }
    }
}
