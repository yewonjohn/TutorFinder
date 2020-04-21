package com.example.android.tutorfinder.ui.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.tutorfinder.R
import com.example.android.tutorfinder.databinding.ActivityLoginBinding
import com.example.android.tutorfinder.ui.home.HomePageActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener,AuthListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //mapping viewmodel to this ui page
        val binding : ActivityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.authListener = this


        //triggering onClicklistener for keyboard minimizing
        loginLayout.setOnClickListener(this)


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
        progress_bar.visibility = View.VISIBLE
    }

    override fun onSuccess(loginResponse: LiveData<String>) {
        loginResponse.observe(this, Observer {
            progress_bar.visibility = View.GONE
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
            val intent = Intent(this, HomePageActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        })
    }

    override fun onFailiure(message:String) {
        progress_bar.visibility = View.GONE
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }
}
