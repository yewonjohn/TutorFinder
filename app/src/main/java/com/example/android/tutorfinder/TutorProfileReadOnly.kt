package com.example.android.tutorfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import com.parse.FindCallback
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_list_of_tutors.*
import kotlinx.android.synthetic.main.activity_tutor_profile_read_only.*

class TutorProfileReadOnly : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_profile_read_only)

        //declaring nameTextView
        var nameTextView: TextView= findViewById(R.id.nameTextView)

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
