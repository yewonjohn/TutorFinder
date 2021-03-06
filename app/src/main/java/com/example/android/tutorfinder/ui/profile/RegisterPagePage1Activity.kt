package com.example.android.tutorfinder.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.tutorfinder.R
import com.example.android.tutorfinder.databinding.ActivityRegisterPage1Binding
import kotlinx.android.synthetic.main.activity_register.*

class RegisterPagePage1Activity : AppCompatActivity(), RegisterPageListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page1)
        val binding: ActivityRegisterPage1Binding = DataBindingUtil.setContentView(this,R.layout.activity_register_page1)
        val viewModel = ViewModelProviders.of(this).get(RegisterPage1ViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.RegisterPageListener = this

    }

    fun doItLater(view: View){
        val intent = Intent(this, RegisterPagePage2Activity::class.java)
        startActivity(intent)
    }

    override fun onStarted() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun onSuccess(response: LiveData<String>) {
        response.observe(this, Observer {
            progress_bar.visibility = View.GONE
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
            val intent = Intent(this, RegisterPagePage2Activity::class.java)
            startActivity(intent)
        })
    }

    override fun onFailiure(message: String) {
        progress_bar.visibility = View.GONE
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }
}
