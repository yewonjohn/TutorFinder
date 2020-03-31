package com.example.android.tutorfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.ParseUser
import com.parse.SaveCallback
import kotlinx.android.synthetic.main.activity_register_page1.*

class RegisterPage1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page1)

        var nameEditText = findViewById<EditText>(R.id.nameEditText)
        var emailEditText = findViewById<EditText>(R.id.profileEmailEditText)
        var phoneNumberEditText = findViewById<EditText>(R.id.phoneNumberEditText)
        var zipCodeEditText = findViewById<EditText>(R.id.zipcodeEditText)
        var nextButton = findViewById<Button>(R.id.nextButton1)

        var currentUser = ParseUser.getCurrentUser()

        nextButton.setOnClickListener(){
            currentUser.put("name",nameEditText.text.toString())
            currentUser.put("email",emailEditText.text.toString())
            currentUser.put("zipcode",zipCodeEditText.text.toString())
            //condition to check if string is not empty.
            if(phoneNumberEditText.text.isNotBlank() || phoneNumberEditText.text.isNotEmpty()) {
                currentUser.put("phoneNumber", phoneNumberEditText.text.toString().toInt())
            }


            currentUser.saveInBackground(SaveCallback { e -> Unit
                if(e === null){
                    Log.i("data","successfully saved")
                    Toast.makeText(this,"Saved!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, RegisterPage2Activity::class.java)
                    startActivity(intent)
                } else {
                    Log.i("failed", "unsuccessful in saving user data")
                    e.printStackTrace()
                }
            })
        }

    }

    fun doItLater(view: View){
        val intent = Intent(this, RegisterPage2Activity::class.java)
        startActivity(intent)
    }
}
