package com.example.android.tutorfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.parse.Parse
import com.parse.ParseUser

class TutorProfile : AppCompatActivity() {

    //displaying and initiating options menu if signed in
    var mainActivity:MainActivity = MainActivity()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (mainActivity.loggedInStatus === true) {
            var menuInflater: MenuInflater = menuInflater
            menuInflater.inflate(R.menu.menu_options, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (mainActivity.loggedInStatus === true) {
            if (item?.itemId === R.id.logout) {
                ParseUser.logOutInBackground() { e ->
                    Unit
                    if (e === null) {
                        var mainActivity: MainActivity = MainActivity()
                        mainActivity.loggedInStatus = false;
                    } else {
                        Log.i("error with signing out", e.printStackTrace().toString())
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_profile)

        //Log.i("current user",ParseUser.getCurrentUser().username.toString())
    }
}
