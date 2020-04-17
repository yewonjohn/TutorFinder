package com.example.android.tutorfinder.ui.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.tutorfinder.R
import com.example.android.tutorfinder.ui.profile.RegisterPage1Activity
import com.example.android.tutorfinder.databinding.ActivityLoginBinding
import com.example.android.tutorfinder.databinding.ActivityRegisterBinding
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.progress_bar

class RegisterActivity : AppCompatActivity(), View.OnKeyListener, View.OnClickListener,
    AuthListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityRegisterBinding = DataBindingUtil.setContentView(this,R.layout.activity_register)
        val viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.authListener = this

        //DECLARING layout of registration
        var registrationLayout = findViewById<ConstraintLayout>(R.id.registrationLayout)

        //DECLARING THE USERNAME + PASSWORD TEXTS + PASSWORD CONFIRM + email
        var usernameEditText: EditText = findViewById(R.id.usernameEditText)
        var passwordEditText: EditText = findViewById(R.id.passwordEditText)
        var confirmPasswordEditText: EditText = findViewById(R.id.confirmPasswordEditText)
        var registerButton = findViewById<Button>(R.id.registerButton)

        //triggering onClicklistener for keyboard minimizing
        registrationLayout.setOnClickListener(this)
        //triggering onKeyListener to press "enter" on keyboard for registration
        passwordEditText.setOnKeyListener(this)
    }



    //setting keyboard management functionality here
    override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
        val viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        if(p1 === KeyEvent.KEYCODE_ENTER && p2?.action === KeyEvent.ACTION_DOWN){
            //this may need to change...
            viewModel.onRegisterButtonClicked(registerButton)
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

    override fun onStarted() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun onSuccess(registerResponse: LiveData<String>) {
        registerResponse.observe(this, Observer {
            progress_bar.visibility = View.GONE
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
            val intent = Intent(this, RegisterPage1Activity::class.java)
            startActivity(intent)
        })
    }

    override fun onFailiure(message: String) {
        progress_bar.visibility = View.GONE
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

}
