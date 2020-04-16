package com.example.android.tutorfinder.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.android.tutorfinder.R
import com.example.android.tutorfinder.TutorProfile
import com.example.android.tutorfinder.databinding.ActivityLoginBinding
import com.parse.LogInCallback
import com.parse.ParseUser

class LoginActivity : AppCompatActivity(), View.OnClickListener,AuthListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //mapping viewmodel to this ui page
        val binding : ActivityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.authListener = this


        //triggering onClicklistener for keyboard minimizing
        var loginLayout = findViewById<ConstraintLayout>(R.id.loginLayout)
        loginLayout.setOnClickListener(this)

        var username = findViewById<EditText>(R.id.usernameLoginEditText)
        var password = findViewById<EditText>(R.id.passwordLoginEditText)


        // setting login button to sign in user
        var LoginButton = findViewById<Button>(R.id.loginButton)
//        LoginButton.setOnClickListener{
//
//            ParseUser.logInInBackground(username.text.toString(),password.text.toString(),
//                LogInCallback { user, e ->  Unit
//
//                if(e === null){
//                    Log.i("login","success!")
//                    Toast.makeText(applicationContext,"login success",Toast.LENGTH_SHORT).show()
//                    val intent = Intent(this, TutorProfile::class.java)
//                    startActivity(intent)
//                } else{
//                    Log.i("login Failed",e.printStackTrace().toString())
//                    Toast.makeText(applicationContext,e.message.toString(),Toast.LENGTH_SHORT).show()
//                }
//                })
//        }
    }
    //onclick for keyboard management
    override fun onClick(p0: View?) {
        if(p0?.id === R.id.loginLayout){
            var inputMethodManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if(currentFocus !== null) {
                inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
            }
        }
    }

    override fun onStarted() {
        Toast.makeText(this,"started",Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess() {
        Toast.makeText(this,"success",Toast.LENGTH_SHORT).show()
    }

    override fun onFailiure(message:String) {
        Toast.makeText(this,"failedd",Toast.LENGTH_SHORT).show()
    }
}
