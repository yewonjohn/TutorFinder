package com.example.android.tutorfinder.ui.profile

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.tutorfinder.R
import com.example.android.tutorfinder.databinding.ActivityLoginBinding
import com.example.android.tutorfinder.databinding.ActivityTutorProfileBinding
import com.example.android.tutorfinder.ui.auth.LoginViewModel
import com.example.android.tutorfinder.ui.home.HomePageActivity
import com.parse.GetDataCallback
import com.parse.ParseFile
import com.parse.ParseUser
import com.parse.SaveCallback
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.progress_bar
import kotlinx.android.synthetic.main.activity_tutor_profile.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class TutorProfileActivity : AppCompatActivity(), View.OnClickListener, ProfileListener,GetProfileListener,GetImageListener {


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
                        val intent = Intent(this, HomePageActivity::class.java)
                        startActivity(intent)
                    } else {
                        Log.i("error with signing out", e.printStackTrace().toString())
                    }
                }
            }
        if (item?.itemId === R.id.myProfile) {
                    val intent = Intent(this, TutorProfileActivity::class.java)
                    startActivity(intent)
        }
        if (item?.itemId === R.id.home) {
                    val intent = Intent(this, HomePageActivity::class.java)
                    startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityTutorProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_tutor_profile)
        val viewModel = ViewModelProviders.of(this).get(TutorProfileViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.ProfileListener = this
        viewModel.GetProfileListener = this
        viewModel.GetImageListener = this

        //fetching current user data from ViewModel --> repository
        TutorProfileViewModel().getInfo()
        TutorProfileViewModel().getProfileImage()

        //triggering onClicklistener for keyboard minimizing
        var profileScrollViewConstraintLayout =
            findViewById<ConstraintLayout>(R.id.profileScrollViewConstraintLayout)
        profileScrollViewConstraintLayout.setOnClickListener(this)

        //initializing actionBar
        setSupportActionBar(findViewById(R.id.app_toolbar))


    }
    //initiating intent to fetch Image on Button Clicked
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
        val selectedImage = data?.data
        if(requestCode === 1 && resultCode === Activity.RESULT_OK && data !== null){
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,selectedImage)
                val imageView = findViewById<ImageView>(R.id.profileImageView)
                imageView.setImageBitmap(bitmap)

                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG,100,stream)

                val byteArray = stream.toByteArray()
                val file = ParseFile("profile_img.png",byteArray)

                val currentUser = ParseUser.getCurrentUser()
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
    override fun onClick(p0: View?) {
        if(p0?.id === R.id.profileScrollViewConstraintLayout || p0?.id === R.id.profileImageView){
            var inputMethodManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if(currentFocus !== null) {
                inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
            }
        }
    }
// SETTING USER INFO ON SAVE CLICK RESPONSE: this worked--
    override fun onStarted() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun onSuccess(response: LiveData<ParseUser>?) {
        progress_bar.visibility = View.GONE
        Toast.makeText(this, response?.value?.get("name").toString()+"'s info saved",Toast.LENGTH_SHORT).show()

    }

    override fun onFailiure(message: String) {
        progress_bar.visibility = View.GONE
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

// GETTING USER DATA AND SETTING IT TO DISPLAY RESPONSE:
    override fun onGETStarted() {
        progress_bar.visibility = View.VISIBLE
    Log.i("getStarted","called")
    }

    override fun onGETSuccess(response: LiveData<ParseUser>) {
        progress_bar.visibility = View.GONE
        Log.i("testg","test")
        response.observe(this, Observer{
            try {
                Log.i("name:",it.get("name").toString())
                nameEditText.setText(it.get("name").toString())
                ageEditText.setText(it.get("age").toString())
                profileEmailEditText.setText(it.get("email").toString())
                zipcodeEditText.setText(it.get("zipcode").toString())
                phoneNumberEditText.setText(it.get("phoneNumber").toString())
                SubjectsEditText.setText(it.get("subjects").toString())
                highestDegreeEditText.setText(it.get("highestDegree").toString())
                SchoolEditText.setText(it.get("school").toString())
                graduationEditText.setText(it.get("graduationDate").toString())
                costRateEditText.setText(it.get("cost").toString())
                descriptionEditText.setText(it.get("description").toString())
            } catch (e:Exception){
                Toast.makeText(this,"Error in fetching user data",Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        })
    }

    override fun onGETFailiure(message: String) {
        progress_bar.visibility = View.GONE
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    //GETTING PROFILE IMAGE AND SETTING IT
    override fun onGetImgStarted() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun onGetImgSuccess(response: Bitmap?) {
        progress_bar.visibility = View.GONE
        profileImageView.setImageBitmap(response)
    }

    override fun onGetImgFailiure(message: String) {
        progress_bar.visibility = View.GONE
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()    }
}
