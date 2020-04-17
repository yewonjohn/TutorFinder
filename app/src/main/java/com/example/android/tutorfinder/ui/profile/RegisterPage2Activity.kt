package com.example.android.tutorfinder.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.android.tutorfinder.R
import com.parse.ParseUser
import com.parse.SaveCallback
import kotlinx.android.synthetic.main.activity_register_page2.*

class RegisterPage2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page2)


        var currentUser = ParseUser.getCurrentUser()

        nextButton2.setOnClickListener(){
            currentUser.put("subjects",subjectEditText.text.toString())
            currentUser.put("highestDegree",highestLevelEducationEditText.text.toString())
            currentUser.put("school",schoolUniversityEditText.text.toString())
            currentUser.put("graduationDate", graduationDateEditText.text.toString())



            currentUser.saveInBackground(SaveCallback { e -> Unit
                if(e === null){
                    Log.i("data","successfully saved")
                    Toast.makeText(this,"Saved!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, RegisterPage3Activity::class.java)
                    startActivity(intent)
                } else {
                    Log.i("failed", "unsuccessful in saving user data")
                    e.printStackTrace()
                }
            })
        }
    }

    fun doItLater(view: View){
        val intent = Intent(this, RegisterPage3Activity::class.java)
        startActivity(intent)
    }
}
