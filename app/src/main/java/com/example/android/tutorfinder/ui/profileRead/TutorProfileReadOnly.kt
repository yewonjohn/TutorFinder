package com.example.android.tutorfinder.ui.profileRead

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.android.tutorfinder.R
import com.example.android.tutorfinder.ui.home.HomePageActivity
import com.example.android.tutorfinder.ui.profile.TutorProfileActivity
import com.parse.FindCallback
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_tutor_profile_read_only.*


class TutorProfileReadOnly : AppCompatActivity(){

    //displaying and initiating options menu if signed in
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (ParseUser.getCurrentUser() !== null) {
            var menuInflater: MenuInflater = menuInflater
            menuInflater.inflate(R.menu.menu_options, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }
    //defining functionality of each button/options on menu ONCLICK
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId === R.id.logout) {
            ParseUser.logOutInBackground() { e ->
                Unit
                if (e === null) {
                    Log.i("success","in signing out")
                    val intent = Intent(this, HomePageActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.i("error with signing out", e.printStackTrace().toString())
                }
            }
        }
        if (item?.itemId === R.id.myProfile) {
            val intent = Intent(this, TutorProfileActivity::class.java)
            startActivity(intent)
        }
        if (item?.itemId === R.id.home) {
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_profile_read_only)

        var contactFragment =
            ContactTutorFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.profileFrameLayout,contactFragment)
            commit()
        }

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
                        locationTextView.text = user.getString("address")
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

    fun hideFragment(view:View){
            // get the center for the clipping circle
            val cx = profileFrameLayout.width / 2
            val cy = profileFrameLayout.height / 2

            // get the initial radius for the clipping circle
            val initialRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()

            // create the animation (the final radius is zero)
            val anim = ViewAnimationUtils.createCircularReveal(profileFrameLayout, cx, cy, initialRadius, 0f)

            // make the view invisible when the animation is done
            anim.addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    profileFrameLayout.visibility = View.GONE
                }
            })
            // start the animation
            anim.start()

        xButton.visibility = View.GONE
    }

    fun viewFragment(myView:View){


            // get the center for the clipping circle
            val cx = profileFrameLayout.width / 2
            val cy = profileFrameLayout.height / 2

            // get the final radius for the clipping circle
            val finalRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()

            // create the animator for this view (the start radius is zero)
            val anim = ViewAnimationUtils.createCircularReveal(profileFrameLayout, cx, cy, 0f, finalRadius)
            // make the view visible and start the animation
            profileFrameLayout.visibility = View.VISIBLE
            anim.start()

        xButton.visibility = View.VISIBLE

    }

}
