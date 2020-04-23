package com.example.android.tutorfinder.ui.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.tutorfinder.R
import com.example.android.tutorfinder.Tutors
import com.example.android.tutorfinder.databinding.ActivityRegisterPage3Binding
import com.parse.ParseFile
import com.parse.ParseUser
import com.parse.SaveCallback
import kotlinx.android.synthetic.main.activity_register_page3.*
import kotlinx.android.synthetic.main.activity_register_page3.progress_bar
import java.io.ByteArrayOutputStream

class RegisterPagePage3Activity : AppCompatActivity(),RegisterPageListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityRegisterPage3Binding = DataBindingUtil.setContentView(this,R.layout.activity_register_page3)
        val viewModel = ViewModelProviders.of(this).get(RegisterPage3ViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.RegisterPageListener = this


        //Initiating Intent for Image Upload on Button Clicked
        val uploadButton = findViewById<Button>(R.id.addImageButton)
        uploadButton.setOnClickListener(){
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            } else {
                //fetching photo
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, 1)
            }
        }
    }
    //METHOD FOR CHECKING FOR STORAGE PERMISSIONS WHEN BUTTON CLICKED -- moves to intent to upload image
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode === 1){
            if(grantResults.size > 0 && grantResults[0] === PackageManager.PERMISSION_GRANTED){
                val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent,1)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    //setup setting imageView to uploaded image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var selectedImage = data?.data
        if(requestCode === 1 && resultCode === Activity.RESULT_OK && data !== null){
            try {
                var bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,selectedImage)
                var imageView = findViewById<ImageView>(R.id.profileImage1View)
                imageView.setImageBitmap(bitmap)

                var stream = ByteArrayOutputStream()

                bitmap.compress(Bitmap.CompressFormat.PNG,100,stream)
                var byteArray = stream.toByteArray()
                var file = ParseFile("profile_img.png",byteArray)


                var currentUser = ParseUser.getCurrentUser()
                currentUser.put("image",file)
                currentUser.saveInBackground(SaveCallback { e -> Unit
                    if(e === null){
                        Log.i("data","successfully saved image")
                    } else {
                        Log.i("failed", "unsuccessful in saving user image")
                    }
                })

            }catch (e:Exception){
                Log.i("error",e.printStackTrace().toString())
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    fun doItLater(view: View){
        val intent = Intent(this, Tutors::class.java)
        startActivity(intent)
    }

    override fun onStarted() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun onSuccess(response: LiveData<String>) {
        response.observe(this, Observer {
            progress_bar.visibility = View.GONE
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Tutors::class.java)
            startActivity(intent)
        })    }

    override fun onFailiure(message: String) {
        progress_bar.visibility = View.VISIBLE
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }
}
