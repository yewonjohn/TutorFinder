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
import com.example.android.tutorfinder.R
import com.example.android.tutorfinder.Tutors
import com.parse.ParseFile
import com.parse.ParseUser
import com.parse.SaveCallback
import kotlinx.android.synthetic.main.activity_register_page3.*
import java.io.ByteArrayOutputStream

class RegisterPage3Activity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page3)

        //setting age Spinner
        val ageSpinner = findViewById<Spinner>(R.id.age_spinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.age_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            ageSpinner.adapter = adapter
        }

        //setting cost Spinner
        val costSpinner = findViewById<Spinner>(R.id.cost_spinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.cost_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            costSpinner.adapter = adapter
        }

        val currentUser = ParseUser.getCurrentUser()

        nextButton3.setOnClickListener(){
            currentUser.put("description",descriptionEditText2.text.toString())
            //currentUser.put("age",age_spinner.getItemAtPosition())


            currentUser.saveInBackground(SaveCallback { e -> Unit
                if(e === null){
                    Log.i("data","successfully saved")
                    Toast.makeText(this,"Saved!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Tutors::class.java)
                    startActivity(intent)
                } else {
                    Log.i("failed", "unsuccessful in saving user data")
                    e.printStackTrace()
                }
            })
        }

        //Uploading Image on Button Clicked
        val uploadButton = findViewById<Button>(R.id.addImageButton)
        uploadButton.setOnClickListener(){
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            } else {
                //fetching photo
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, 1)
            }
        }
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

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    fun doItLater(view: View){
        val intent = Intent(this, Tutors::class.java)
        startActivity(intent)
    }
}
