package com.example.android.tutorfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.LogInCallback
import com.parse.ParseUser

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var username = findViewById<EditText>(R.id.usernameLoginEditText)
        var password = findViewById<EditText>(R.id.passwordLoginEditText)



        var LoginButton = findViewById<Button>(R.id.loginButton)
        LoginButton.setOnClickListener{

            ParseUser.logInInBackground(username.text.toString(),password.text.toString(),
                LogInCallback { user, e ->  Unit

                if(e === null){
                    Log.i("login","success!")
                    Toast.makeText(applicationContext,"login success",Toast.LENGTH_SHORT).show()
                    var mainActivity:MainActivity = MainActivity()
                    mainActivity.loggedInStatus = true

                    val intent = Intent(this, TutorProfile::class.java)
                    startActivity(intent)
                } else{
                    Log.i("login Failed",e.printStackTrace().toString())
                    Toast.makeText(applicationContext,e.message.toString(),Toast.LENGTH_SHORT).show()
                }
                })
        }
    }
}
