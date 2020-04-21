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

    //CLASS FOR API
    public class DownloadTask: AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg p0: String?): String {

            var result: String = ""
            var url: URL
            var urlConnection: HttpURLConnection

            try {
                url = URL(p0[0])
                urlConnection = url.openConnection() as HttpURLConnection
                var input = urlConnection.inputStream
                var reader: InputStreamReader = InputStreamReader(input)
                var data = reader.read()

                while (data !== -1){
                    var current = data.toChar()
                    result += current
                    data = reader.read()
                }
            } catch (e: Exception){
                Log.i("Error fetching API",e.printStackTrace().toString())
            }

            return result
        }
    //METHOD POST EXECUTE FOR API
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            //Log.i("JSON",result)
        }
    }


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
        val binding : ActivityTutorProfileBinding = DataBindingUtil.setContentView(this,R.layout.activity_tutor_profile)
        val viewModel = ViewModelProviders.of(this).get(TutorProfileViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.ProfileListener = this

        //fetching current user data from ViewModel --> respository
        val displayInfo = TutorProfileViewModel().getInfo()
        val displayImage = TutorProfileViewModel().getProfileImage()




        //triggering onClicklistener for keyboard minimizing
        var profileScrollViewConstraintLayout = findViewById<ConstraintLayout>(R.id.profileScrollViewConstraintLayout)
        profileScrollViewConstraintLayout.setOnClickListener(this)

        //initializing actionBar
        setSupportActionBar(findViewById(R.id.app_toolbar))

        //getting current user
        var currentUser = ParseUser.getCurrentUser()
        //initializing fields from page
        var userAge = findViewById<EditText>(R.id.ageEditText)
        var userName = findViewById<EditText>(R.id.nameEditText)
        var userZipCode = findViewById<EditText>(R.id.zipcodeEditText)
        var userDescription = findViewById<EditText>(R.id.descriptionEditText)
        var userCost = findViewById<EditText>(R.id.costRateEditText)
        var userHighestDegree = findViewById<EditText>(R.id.highestDegreeEditText)
        var userSchool = findViewById<EditText>(R.id.SchoolEditText)
        var userGraduationDate = findViewById<EditText>(R.id.graduationEditText)
        var userSubjects = findViewById<EditText>(R.id.SubjectsEditText)
        var saveButton = findViewById<Button>(R.id.saveButton)
        var profileImage = findViewById<ImageView>(R.id.profileImageView)
        var userEmail = findViewById<EditText>(R.id.profileEmailEditText)
        var userNumber = findViewById<EditText>(R.id.phoneNumberEditText)
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

        try {
            //displaying current user info
            userAge.setText(currentUser.getString("age"))
            userName.setText(currentUser.getString("name"))
            userEmail.setText((currentUser.getString("email")))
            userZipCode.setText(currentUser.getString("zipcode"))
            userDescription.setText(currentUser.getString("description"))
            userCost.setText(currentUser.getString("cost"))
            userHighestDegree.setText(currentUser.getString("highestDegree"))
            userSchool.setText(currentUser.getString("school"))
            userGraduationDate.setText(currentUser.getString("graduationDate"))
            userSubjects.setText(currentUser.getString("subjects"))
            userNumber.setText(currentUser.getInt("phoneNumber").toString())
        } catch (e:Exception){
            e.printStackTrace()
            Toast.makeText(this,"Info not fetched correctly",Toast.LENGTH_SHORT).show()
        }


        //SAVES field data to current User onClick
        saveButton.setOnClickListener(){
            currentUser.put("name",userName.text.toString())
            currentUser.put("age",userAge.text.toString())
            currentUser.put("zipcode",userZipCode.text.toString())
            currentUser.put("description",userDescription.text.toString())
            currentUser.put("cost",userCost.text.toString())
            currentUser.put("email",userEmail.text.toString())
            currentUser.put("highestDegree",userHighestDegree.text.toString())
            currentUser.put("school",userSchool.text.toString())
            currentUser.put("graduationDate",userGraduationDate.text.toString())
            currentUser.put("subjects",userSubjects.text.toString())
            //compiling education into one variable here
            var education = userSchool.text.toString() +" "+userHighestDegree.text.toString()+" "+ userGraduationDate.text.toString()
            currentUser.put("educationDesc",education)

            //API STUFF HERE: calling api then parsing JSON about user location
            var result: String = ""
            try {
                var task =
                    DownloadTask()
                result = task.execute("https://maps.googleapis.com/maps/api/geocode/json?address="+userZipCode.text.toString()+"&key=AIzaSyBlXzJreSsIzhWffbBUlhEcP_Eoc8qIXbM").get()
                Log.i("result of API call",result)
            } catch (e:Exception){
                Log.i("Error fetching API",e.printStackTrace().toString())
            }

            //SET LOCATION HERE
            // parsing the JSON here to "formatted_address"
            try {
                var jsonObject = JSONObject(result)
                var results = jsonObject.getString("results")
                var results1 = JSONArray(results)
                var results2 = JSONObject(results1[0].toString())
                var results3 = results2.getString("formatted_address")
                Log.i("addresss",results3)
                currentUser.put("address",results3)
            }catch (e:Exception){
                e.printStackTrace()
            }

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
    override fun onClick(p0: View?) {
        if(p0?.id === R.id.profileScrollViewConstraintLayout || p0?.id === R.id.profileImageView){
            var inputMethodManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if(currentFocus !== null) {
                inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
            }
        }
    }

    override fun onStarted() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun onSuccess(response: LiveData<ParseUser>) {
        progress_bar.visibility = View.GONE
        TODO("Not yet implemented")
    }

    override fun onFailiure(message: String) {
        progress_bar.visibility = View.GONE
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }


    override fun onGETStarted() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun onGETSuccess(response: LiveData<ParseUser>) {
        progress_bar.visibility = View.GONE
        response.observe(this, Observer{
            try {
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

        TODO("Not yet implemented")
    }

    override fun onGETFailiure(message: String) {
        progress_bar.visibility = View.GONE
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

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
