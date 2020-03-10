package com.example.android.tutorfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import com.parse.FindCallback
import com.parse.ParseQuery
import com.parse.ParseUser

class Tutors : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_tutors)

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
