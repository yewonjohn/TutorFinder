package com.example.android.tutorfinder

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.Parse
import com.parse.ParseUser
import com.parse.SaveCallback
import com.parse.SignUpCallback
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_tutor_profile.*

class TutorProfile : AppCompatActivity(), View.OnClickListener {

    //METHOD FOR CHECKING FOR STORAGE PERMISSIONS -- GOTTA ADD THE CONDITIONS THO
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

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

        //saves field data to current User onClick
        saveButton.setOnClickListener(){
            currentUser.put("name",userName.text.toString())
            currentUser.put("age",userAge.text.toString())
            currentUser.put("location",userLocation.text.toString())
            currentUser.saveInBackground(SaveCallback { e -> Unit
                if(e === null){
                    Log.i("data","successfully saved")
                    Toast.makeText(this,"Saved!",Toast.LENGTH_SHORT).show()
                    } else {
                    Log.i("failed", "unsuccessful in saving user data")
                }
            })
        }
    }

    //setting keyboard interaction for layout (clicking out of keyboard view)
    //THIS IS NOT WORKING RIGHT NOW
    override fun onClick(p0: View?) {
        if(p0?.id === R.id.profileLayout || p0?.id === R.id.profileImageView){
            var inputMethodManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken,0)
            Toast.makeText(this,"Clcked",Toast.LENGTH_SHORT).show()
            Log.i("Something","CLICKED")
        }
    }
}
