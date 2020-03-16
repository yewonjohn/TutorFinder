package com.example.android.tutorfinder

import android.content.Intent
import android.media.Rating
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import com.parse.FindCallback
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_tutor_profile_read_only.*

class TutorProfileReadOnly : AppCompatActivity(){

    //displaying and initiating options menu if signed in
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //this condition will have to change
        if (ParseUser.getCurrentUser() !== null) {
            var menuInflater: MenuInflater = menuInflater
            menuInflater.inflate(R.menu.menu_options, menu)
        }
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
        //profile icon takes you to myProfile
        if (item?.itemId === R.id.myProfile) {
            val intent = Intent(this, TutorProfile::class.java)
            startActivity(intent)
        }
        //home icon takes you to home
        if (item?.itemId === R.id.home) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_profile_read_only)

        //TEMPORARY RATING SYSTEM
        var ratingbar = findViewById<RatingBar>(R.id.ratingBar)
        var ratingButton = findViewById<TextView>(R.id.ratingTextButton)
        var ratingTextView = findViewById<TextView>(R.id.ratingTextView)

        ratingButton.setOnClickListener(View.OnClickListener {
            ratingTextView.text = ratingbar.rating.toString()
        })


        //initializing actionBar
        setSupportActionBar(findViewById(R.id.app_toolbar))

        //declaring nameTextView
        var nameTextView: TextView= findViewById(R.id.nameAgeTextView)

        //Fetching/getting info from passed intent
        var intent = intent
        var selectedUser = intent.getStringExtra("objectId")
        //querying data to acquire the selected username
        val query: ParseQuery<ParseUser> = ParseUser.getQuery()
        query.whereEqualTo("objectId",selectedUser)
        query.findInBackground(FindCallback { objects, e -> Unit
            if(e === null){
                if(objects.size > 0){
                    for(user: ParseUser in objects){
                        //setting the fields based on the user info!
                        nameAgeTextView.text = user.getString("name")+", "+user.getString("age")
                        locationTextView.text = user.getString("location")
                        descriptionTextView.text = user.getString("description")
                        costTextView.text = "Avg $"+user.getString("cost")+" /hr"
                        educationTitleTextView.text = user.getString("name")+"'s Education"
                        educationTextView.text = user.getString("educationDesc")
                        subjectTitleTextView.text = user.getString("name")+"'s Subjects"
                        //setting every subject button
                        //THIS WILL CHANGE. I HAVE MADE THIS AS JUST A LINE OF STRINGS FOR NOW
                        var subjects = user.getString("subjects")?.split(",")

                        when (subjects?.size){
                            0 -> {
                                Log.i("subjects:","nothing here")
                            }
                            1 -> {
                                subject1_button.text = subjects?.get(0)
                            }
                            2 -> {
                                subject1_button.text = subjects?.get(0)
                                subject2_button.text = subjects?.get(1)
                            }
                            3 -> {
                                subject1_button.text = subjects?.get(0)
                                subject2_button.text = subjects?.get(1)
                                subject3_button.text = subjects?.get(2)
                            }
                            4 -> {
                                subject1_button.text = subjects?.get(0)
                                subject2_button.text = subjects?.get(1)
                                subject3_button.text = subjects?.get(2)
                                subject4_button.text = subjects?.get(3)
                            }
                            5 -> {
                                subject1_button.text = subjects?.get(0)
                                subject2_button.text = subjects?.get(1)
                                subject3_button.text = subjects?.get(2)
                                subject4_button.text = subjects?.get(3)
                                subject5_button.text = subjects?.get(4)
                            }
                            6 -> {
                                subject1_button.text = subjects?.get(0)
                                subject2_button.text = subjects?.get(1)
                                subject3_button.text = subjects?.get(2)
                                subject4_button.text = subjects?.get(3)
                                subject5_button.text = subjects?.get(4)
                                subject6_button.text = subjects?.get(5)
                            }
                        }
                    }
                    //MAKING SUBJECT BUTTONS VISIBLE IF TEXT EXISTS
                    if(subject1_button.text.isNotEmpty()){
                        subject1_button.visibility = View.VISIBLE
                    }
                    if(subject2_button.text.isNotEmpty()){
                        subject2_button.visibility = View.VISIBLE
                    }
                    if(subject3_button.text.isNotEmpty()){
                        subject3_button.visibility = View.VISIBLE
                    }
                    if(subject4_button.text.isNotEmpty()){
                        subject4_button.visibility = View.VISIBLE
                    }
                    if(subject5_button.text.isNotEmpty()){
                        subject5_button.visibility = View.VISIBLE
                    }
                    if(subject6_button.text.isNotEmpty()){
                        subject6_button.visibility = View.VISIBLE
                    }
                }
            }else{
                Log.i("query failed",e.printStackTrace().toString())
            }
        })

    }

}
