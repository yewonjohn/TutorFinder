package com.example.android.tutorfinder

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.parse.GetDataCallback
import com.parse.ParseFile
import com.parse.ParseUser
import com.parse.SaveCallback
import java.io.ByteArrayOutputStream

class TutorProfile : AppCompatActivity(), View.OnClickListener {

    //displaying and initiating options menu if signed in
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //there's no condition for this yet.
            var menuInflater: MenuInflater = menuInflater
            menuInflater.inflate(R.menu.menu_options, menu)

        return super.onCreateOptionsMenu(menu)
    }

    //when loggedout, intent takes you back to homepage
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
            if (item?.itemId === R.id.logout) {
                ParseUser.logOutInBackground() { e ->
                    Unit
                    if (e === null) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Log.i("error with signing out", e.printStackTrace().toString())
                    }
                }
            }
        if (item?.itemId === R.id.myProfile) {
                    val intent = Intent(this, TutorProfile::class.java)
                    startActivity(intent)
        }
        if (item?.itemId === R.id.home) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_profile)

        //initializing actionBar
        setSupportActionBar(findViewById(R.id.app_toolbar))
        //getting current user
        var currentUser = ParseUser.getCurrentUser()
        //initializing fields from page
        var userAge = findViewById<EditText>(R.id.ageEditText)
        var userName = findViewById<EditText>(R.id.nameEditText)
        var userLocation = findViewById<EditText>(R.id.locationEditText)
        var saveButton = findViewById<Button>(R.id.saveButton)
        var profileImage = findViewById<ImageView>(R.id.profileImageView)
        //displaying current user image
        var file = currentUser.getParseFile("image")
        file?.getDataInBackground(GetDataCallback { data, e ->
            if (e == null) {
                // Decode the Byte[] into bitmap
                val bmp: Bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
                // Set the Bitmap into the imageView
                profileImage.setImageBitmap(bmp)
                Log.i("success","Image fetched correctly")
            } else {
                Log.d("test", "There was a problem downloading the data.")
            }
        })
        //displaying current user age
        userAge.setText(currentUser.getString("age"))
        //displaying current user name
        userName.setText(currentUser.getString("name"))
        //displaying current user location
        userLocation.setText(currentUser.getString("location"))
        //saves field data to current User onClick
        saveButton.setOnClickListener(){
            currentUser.put("name",userName.text.toString())
            currentUser.put("age",userAge.text.toString())
            currentUser.put("location",userLocation.text.toString())
            currentUser.saveInBackground(SaveCallback { e -> Unit
                if(e === null){
                    Log.i("data","successfully saved")
                    Toast.makeText(this,"Saved!",Toast.LENGTH_SHORT).show()
                    } else {
                    Log.i("failed", "unsuccessful in saving user data")
                }
            })
        }
    }
    //Uploading Image on Button Clicked
    fun getPhoto(view:View){
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else{
            //fetching photo
            val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,1)
        }
    }

    //setup setting imageView to uploaded image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var selectedImage = data?.data
        if(requestCode === 1 && resultCode === Activity.RESULT_OK && data !== null){
            try {
                var bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,selectedImage)
                var imageView = findViewById<ImageView>(R.id.profileImageView)
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

    //setting keyboard interaction for layout (clicking out of keyboard view)
    //THIS IS NOT WORKING RIGHT NOW-- NOT WORKING
    override fun onClick(p0: View?) {
        if(p0?.id === R.id.profileLayout || p0?.id === R.id.profileImageView){
            var inputMethodManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken,0)
            Toast.makeText(this,"Clicked",Toast.LENGTH_SHORT).show()
            Log.i("Something","CLICKED")
        }
    }
}
