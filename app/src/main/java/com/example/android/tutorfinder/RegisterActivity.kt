package com.example.android.tutorfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.ParseException
import com.parse.ParseUser
import com.parse.SignUpCallback
import kotlinx.android.synthetic.main.activity_login.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val usernameEditText: EditText = findViewById(R.id.usernameEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)

//        fun signUpClicked(view: View){
//            val usernameEditText: EditText = findViewById(R.id.nameEditText)
//            val passwordEditText: EditText = findViewById(R.id.passwordEditText)
//
//            if(usernameEditText.text.isEmpty() or passwordEditText.text.isEmpty()){
//                Toast.makeText(this,"A username and password are required",Toast.LENGTH_SHORT)
//
//            }
//            else{
//                val user = ParseUser()
//                user.username = usernameEditText.toString()
//                user.setPassword(passwordEditText.toString())
//                user.signUpInBackground(object:SignUpCallback {
//                    override fun done(e:ParseException) {
//                        if (e == null){
//                            Log.i("Sign-Up","is a success!")
//                            val intent = Intent(applicationContext, LoginActivity::class.java)
//                            startActivity(intent)
//                        } else{
//                            Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show()
//                        }
//                    }})
//            }
//        }

        val registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener{
            if(usernameEditText.text.isEmpty() or passwordEditText.text.isEmpty()){
                Toast.makeText(this,"A username and password are required",Toast.LENGTH_SHORT)
            }
            else{
                val user = ParseUser()
                user.username = usernameEditText.toString()
                user.setPassword(passwordEditText.toString())
                user.signUpInBackground(object:SignUpCallback {
                    override fun done(e:ParseException) {
                        if (e == null){
                            Log.i("Sign-Up","is a success!")
                            val intent = Intent(applicationContext, LoginActivity::class.java)
                            startActivity(intent)
                        } else{
                            Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show()
                        }
                    }})
            }
        }
    }
}
