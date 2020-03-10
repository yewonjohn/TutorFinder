package com.example.android.tutorfinder

import android.content.Context
import android.content.Intent
import android.hardware.input.InputManager
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
import com.parse.LogInCallback
import com.parse.ParseException
import com.parse.ParseUser
import com.parse.SignUpCallback
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), View.OnKeyListener, View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

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


    //BUTTON TO REGISTER
    fun register(view: View) {
            //checks if every field is filled in
            if (usernameEditText.text.isEmpty() || passwordEditText.text.toString().isBlank() || confirmPasswordEditText.text.isEmpty() || emailEditText.text.isEmpty()) {
                Toast.makeText(this, "Please fill in every field", Toast.LENGTH_SHORT).show()
            }
            //checks if email is valid
            else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailEditText.text.toString()).matches()){
                Toast.makeText(this, "EMAIL ERROR",Toast.LENGTH_SHORT).show()
            }
            else {
                //checks if passwords are matching, then creates user
                if (passwordEditText.text.toString().equals(confirmPasswordEditText.text.toString())) {
                    var user = ParseUser()
                    user.email = emailEditText.text.toString()
                    user.username = usernameEditText.text.toString()
                    user.setPassword(passwordEditText.text.toString())
                    user.signUpInBackground { e ->
                        Unit
                        if (e === null) {
                            Log.i("Sign-Up", "is a success!")
                            //LOG THE USER IN AUTOMATICALLY HERE
                            ParseUser.logInInBackground(usernameEditText.text.toString(),passwordEditText.text.toString(),
                                LogInCallback { user, e ->  Unit
                                    if(e === null){
                                        Log.i("login","success!")
                                        Toast.makeText(applicationContext,"login success",Toast.LENGTH_SHORT).show()
                                        val intent = Intent(this, TutorProfile::class.java)
                                        startActivity(intent)
                                    } else{
                                        Log.i("login Failed",e.printStackTrace().toString())
                                        Toast.makeText(applicationContext,e.message.toString(),Toast.LENGTH_SHORT).show()
                                    }
                                })
                            //EXCEPTIONS FOR LOGIN
                        } else {
                            Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                //password and confirm password fields are mismatching *EXCEPTIONS FOR REGISTER
                else{
                    Toast.makeText(this,"Passwords are not matching!",Toast.LENGTH_SHORT).show()
                }
            }

    }
    //setting keyboard management functionality here
    override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
        if(p1 === KeyEvent.KEYCODE_ENTER && p2?.action === KeyEvent.ACTION_DOWN){
            register(registerButton)
            Toast.makeText(applicationContext,"onKeyTriggered",Toast.LENGTH_SHORT).show()
        }
        return false
    }
    //setting keyboard interaction for layout (clicking out of keyboard view)
    override fun onClick(p0: View?) {
        if(p0?.id === R.id.registrationLayout){
            var inputMethodManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken,0)
        }
    }

}
