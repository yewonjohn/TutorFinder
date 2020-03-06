package com.example.android.tutorfinder

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var ImageView = findViewById<ImageView>(R.id.imageView)
        ImageView.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.study_background2))





    }

    fun findTutors(view: View){

    }

    fun registerTutor(view: View){

    }

    fun logInAsTutor(view: View){

    }


}
