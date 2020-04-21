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
import com.example.android.tutorfinder.databinding.ActivityRegisterPage2Binding
import kotlinx.android.synthetic.main.activity_register_page2.progress_bar

class RegisterPagePage2Activity : AppCompatActivity(), RegisterPageListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityRegisterPage2Binding = DataBindingUtil.setContentView(this,R.layout.activity_register_page2)
        val viewModel = ViewModelProviders.of(this).get(RegisterPage2ViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.RegisterPageListener = this

    }

    fun doItLater(view: View){
        val intent = Intent(this, RegisterPagePage3Activity::class.java)
        startActivity(intent)
    }

    override fun onStarted() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun onSuccess(response: LiveData<String>) {
        response.observe(this, Observer {
            progress_bar.visibility = View.GONE
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
            val intent = Intent(this, RegisterPagePage3Activity::class.java)
            startActivity(intent)
        })
    }

    override fun onFailiure(message: String) {
        progress_bar.visibility = View.GONE
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()    }
}
