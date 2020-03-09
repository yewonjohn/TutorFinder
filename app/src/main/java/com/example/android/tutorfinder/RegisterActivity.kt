package com.example.android.tutorfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.ParseException
import com.parse.ParseUser
import com.parse.SignUpCallback
import kotlinx.android.synthetic.main.activity_login.*

class RegisterActivity : AppCompatActivity(), View.OnKeyListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //DECLARING THE USERNAME + PASSWORD TEXTS
        var usernameEditText: EditText = findViewById(R.id.usernameEditText)
        var passwordEditText: EditText = findViewById(R.id.passwordEditText)

        passwordEditText.setOnKeyListener(this)

        //BUTTON TO REGISTER
        var registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener{
            if(usernameEditText.text.isEmpty() || passwordEditText.text.toString().isBlank()){
                Toast.makeText(this,"A username and password are required",Toast.LENGTH_SHORT).show()
            }
            else{
                var user = ParseUser()
                user.username = usernameEditText.text.toString()
                user.setPassword(passwordEditText.text.toString())
                user.signUpInBackground { e -> Unit
                    if (e === null){
                        Log.i("Sign-Up","is a success!")
                        val intent = Intent(applicationContext, LoginActivity::class.java)
                        startActivity(intent)
                    } else{
                        Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
        TODO("Not yet implemented")
        if(p1 === p2.keyCode)
    }
}
