package com.example.android.tutorfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.Parse
import com.parse.ParseUser
import com.parse.SaveCallback
import com.parse.SignUpCallback
import kotlinx.android.synthetic.main.activity_tutor_profile.*

class TutorProfile : AppCompatActivity() {

    //displaying and initiating options menu if signed in
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //there's no condition for this yet.
            var menuInflater: MenuInflater = menuInflater
            menuInflater.inflate(R.menu.menu_options, menu)

        return super.onCreateOptionsMenu(menu)
    }
    //when loggedout, intent takes you back to homepage
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
            if (item?.itemId === R.id.logout) {
                ParseUser.logOutInBackground() { e ->
                    Unit
                    if (e === null) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Log.i("error with signing out", e.printStackTrace().toString())
                    }
                }
            }
        if (item?.itemId === R.id.myProfile) {
                    val intent = Intent(this, TutorProfile::class.java)
                    startActivity(intent)
        }
        if (item?.itemId === R.id.home) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_profile)

        //initializing actionBar
        setSupportActionBar(findViewById(R.id.app_toolbar))

        //getting current user
        var currentUser = ParseUser.getCurrentUser()

        //initializing fields from page
        var userAge = findViewById<EditText>(R.id.ageEditText)
        var userName = findViewById<EditText>(R.id.nameEditText)
        var userLocation = findViewById<EditText>(R.id.locationEditText)

        var saveButton = findViewById<Button>(R.id.saveButton)

        //displaying current data first
        userAge.setText(currentUser.getString("age"))
        userName.setText(currentUser.getString("name"))
        userLocation.setText(currentUser.getString("location"))

        //saves field data to current User ONCHANGE
        userAge.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                currentUser.put("age",userAge.text.toString())
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        userName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                currentUser.put("name",userName.text.toString())
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        userName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                currentUser.put("location",userLocation.text.toString())
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        saveButton.setOnClickListener(){
            currentUser.saveInBackground(SaveCallback { e -> Unit
                if(e === null){
                    Log.i("data","successfully saved")
                    Toast.makeText(this,"Saved!",Toast.LENGTH_SHORT)
                    } else {
                    Log.i("failed", "unsuccessful in saving user data")
                }
            })
        }
    }
}
