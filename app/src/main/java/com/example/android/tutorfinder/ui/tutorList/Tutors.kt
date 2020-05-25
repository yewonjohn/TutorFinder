package com.example.android.tutorfinder.ui.tutorList

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ListView
import com.example.android.tutorfinder.CustomListView
import com.example.android.tutorfinder.R
import com.example.android.tutorfinder.ui.profileRead.TutorProfileReadOnly
import com.example.android.tutorfinder.ui.home.HomePageActivity
import com.example.android.tutorfinder.ui.profile.TutorProfileActivity
import com.parse.FindCallback
import com.parse.GetDataCallback
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
        setContentView(R.layout.activity_list_of_tutors)

        var userNames = ArrayList<String>()
        var userLocation = ArrayList<String>()
        var userPrice = ArrayList<String>()
        var userImage = ArrayList<Bitmap>()

        //initializing actionBar
        setSupportActionBar(findViewById(R.id.app_toolbar))

        //declaring listView
        val listView = findViewById<ListView>(R.id.listView)

        //declaring the array that will hold all the queried users objectId
        val listOfTutorsObjectId = ArrayList<String>()
        //initializing adapter for listView
        val adapter = CustomListView(
            this,
            userNames,
            userLocation,
            userPrice,
            userImage
        )

        //filter by username ascending order
        val query1: ParseQuery<ParseUser> = ParseUser.getQuery()
        query1.addAscendingOrder("objectId")
        query1.findInBackground(FindCallback { objects, e -> Unit
            if(e === null){
                if(objects.size > 0){
                    for(user: ParseUser in objects){
                        listOfTutorsObjectId.add(user.objectId.toString())
                        userNames.add(user.getString("name").toString())
                        userLocation.add(user.getString("location").toString())
                        userPrice.add("Avg $"+user.getString("cost").toString()+" /hr")
                        //adding profile pix to ImageArray
                        var file = user.getParseFile("image")
                        file?.getDataInBackground(GetDataCallback { data, e ->
                            if (e == null) {
                                // Decode the Byte[] into bitmap
                                val bmp: Bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
                                // Set the Bitmap into the BitMap Array for Profile Images
                                userImage.add(bmp)
                            } else {
                                Log.d("problem with downloading img", e.printStackTrace().toString())
                            }
                        })
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
            intent.putExtra("objectId", listOfTutorsObjectId[position])
            startActivity(intent)
        }
    }
}