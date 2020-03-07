package com.example.android.tutorfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class Tutors : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_tutors)


        val listOfTutors = ArrayList<String>()
        listOfTutors.add("Mike")
        listOfTutors.add("Jackie")
        listOfTutors.add("John")

        val arrayOfTutors = arrayOfNulls<String>(listOfTutors.size)
        listOfTutors.toArray(arrayOfTutors)

        val adapter = ArrayAdapter<String>(this, R.layout.listview_item, arrayOfTutors)

        val listView = findViewById<ListView>(R.id.listView)
        listView.setAdapter(adapter)


        listView.setOnItemClickListener{ parent, view, position, id ->
            //getting item selected (returning a long?)
            val selectedItemPosition = parent.getItemIdAtPosition(position)

            //intent to profile activity
            val intent = Intent(this, TutorProfile::class.java)
            startActivity(intent)

        }
    }

}
