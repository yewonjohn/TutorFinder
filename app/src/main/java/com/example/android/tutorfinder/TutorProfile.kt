package com.example.android.tutorfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import com.parse.Parse
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_tutor_profile.*

class TutorProfile : AppCompatActivity() {

    //displaying and initiating options menu if signed in
    var mainActivity:MainActivity = MainActivity()
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
                        var mainActivity: MainActivity = MainActivity()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Log.i("error with signing out", e.printStackTrace().toString())
                    }
                }
            }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_profile)

        var homeButton = findViewById<Button>(R.id.homeButton)

        homeButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}
