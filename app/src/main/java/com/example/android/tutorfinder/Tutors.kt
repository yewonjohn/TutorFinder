package com.example.android.tutorfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import com.parse.FindCallback
import com.parse.Parse
import com.parse.ParseQuery
import com.parse.ParseUser

class Tutors : AppCompatActivity() {

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
        setContentView(R.layout.activity_list_of_tutors)


        //initializing actionBar
        setSupportActionBar(findViewById(R.id.app_toolbar))


        //declaring listView
        val listView = findViewById<ListView>(R.id.listView)

        //declaring the array that will hold all the queried users
        val listOfTutors = ArrayList<String>()
        //val arrayOfTutors = arrayOfNulls<String>(listOfTutors.size)
        //listOfTutors.toArray(arrayOfTutors)
        val adapter = ArrayAdapter<String>(this, R.layout.listview_item, listOfTutors)


        val query: ParseQuery<ParseUser> = ParseUser.getQuery()
        //avoid logged in user displaying
        //query.whereNotEqualTo("username",ParseUser.getCurrentUser().username)
        //filter by username ascending order
        query.addAscendingOrder("username")
        query.findInBackground(FindCallback { objects, e -> Unit
            if(e === null){
                if(objects.size > 0){
//                    var i = 0
                    for(user: ParseUser in objects){
                        listOfTutors.add(user.username)
//                        Log.i("users",listOfTutors.elementAt(i).toString())
//                        i++
                    }
                    listView.setAdapter(adapter)
                }
            }else{
                Log.i("query failed",e.printStackTrace().toString())
            }
        })

        //setting up onClick for list-- clicking name should take you to next page with details
        listView.setOnItemClickListener{ parent, view, position, id ->
            //getting item selected (returning a long?)
            val selectedItemPosition = parent.getItemIdAtPosition(position)

            //intent to profile activity
            val intent = Intent(this, TutorProfileReadOnly::class.java)
            intent.putExtra("username", listOfTutors.get(position))
            startActivity(intent)

        }
    }

}
