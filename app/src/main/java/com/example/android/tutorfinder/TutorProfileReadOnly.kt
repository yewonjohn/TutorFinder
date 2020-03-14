package com.example.android.tutorfinder

import android.content.Intent
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.parse.FindCallback
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_list_of_tutors.*
import kotlinx.android.synthetic.main.activity_tutor_profile_read_only.*

class TutorProfileReadOnly : AppCompatActivity() {

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
        setContentView(R.layout.activity_tutor_profile_read_only)

        //initializing actionBar
        setSupportActionBar(findViewById(R.id.app_toolbar))

        //declaring nameTextView
        var nameTextView: TextView= findViewById(R.id.nameAgeTextView)

        //Fetching/getting info from passed intent
        var intent = getIntent()
        var selectedUsername = intent.getStringExtra("username")
        //querying data to acquire the selected username
        val query: ParseQuery<ParseUser> = ParseUser.getQuery()
        query.whereEqualTo("username",selectedUsername)
        query.findInBackground(FindCallback { objects, e -> Unit
            if(e === null){
                if(objects.size > 0){
                    for(user: ParseUser in objects){
                        //setting the fields based on the user info!
                        nameTextView.setText(user.username.toString())
                    }
                }
            }else{
                Log.i("query failed",e.printStackTrace().toString())
            }
        })
    }
}
