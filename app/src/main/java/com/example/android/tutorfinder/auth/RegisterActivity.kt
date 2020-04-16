package com.example.android.tutorfinder.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.android.tutorfinder.R
import com.example.android.tutorfinder.RegisterPage1Activity
import com.example.android.tutorfinder.databinding.ActivityLoginBinding
import com.example.android.tutorfinder.databinding.ActivityRegisterBinding
import com.parse.LogInCallback
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), View.OnKeyListener, View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityRegisterBinding = DataBindingUtil.setContentView(this,R.layout.activity_register)
        val viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.authListener = this

        //DECLARING layout of registration
        var registrationLayout = findViewById<ConstraintLayout>(R.id.registrationLayout)

        //DECLARING THE USERNAME + PASSWORD TEXTS + PASSWORD CONFIRM + email
        var usernameEditText: EditText = findViewById(R.id.usernameEditText)
        var passwordEditText: EditText = findViewById(R.id.passwordEditText)
        var emailEditText: EditText = findViewById(R.id.emailEditText)
        var confirmPasswordEditText: EditText = findViewById(R.id.confirmPasswordEditText)
        var registerButton = findViewById<Button>(R.id.registerButton)

        //triggering onClicklistener for keyboard minimizing
        registrationLayout.setOnClickListener(this)
        //triggering onKeyListener to press "enter" on keyboard for registration
        passwordEditText.setOnKeyListener(this)
    }



    //setting keyboard management functionality here
    override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
        if(p1 === KeyEvent.KEYCODE_ENTER && p2?.action === KeyEvent.ACTION_DOWN){
            register(registerButton)
        }
        return false
    }
    //setting keyboard interaction for layout (clicking out of keyboard view)
    override fun onClick(p0: View?) {
        if(p0?.id === R.id.registrationLayout){
            var inputMethodManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if(currentFocus !== null) {
                inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
            }
        }
    }

}
