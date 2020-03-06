package com.example.android.tutorfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Tutors : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_tutors)



        val listOfTutors = mutableListOf("Paul","Jackie","Mike")
        listOfTutors.add("Keeyeon")

        
    }

}
