package com.example.android.tutorfinder

import android.app.Application
import android.util.Log
import com.parse.*


class ParseStarterApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this)

        // Add your initialization code here
        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId("4eb0e90b8e77c2e0dbc2f70632304c93c1ccf4ff")
                .clientKey("e1c1ab85cd386a36b3d7a2e2043c00e5dbec4213")
                .server("http://18.218.244.112:80/parse/")
                .build()
        )
//        val query = ParseQuery.getQuery<ParseObject>("SoccerPlayers")
//        query.whereEqualTo("objectId", "QHjRWwgEtd")
//        query.getFirstInBackground(GetCallback(){
//            fun done (object1: ParseObject, e: ParseException){
//                if (e == null){
//                    val playerName = object1.getString("playerName");
//                } else{
//                    Log.i("something went wrong:",e.printStackTrace().toString())
//                }
//            }
//
//        })

//        val exampleObject = ParseObject("ExampleObject")
//        exampleObject.put("myNumber", "123")
//        exampleObject.put("myString", "rob")
//        exampleObject.saveInBackground { ex ->
//            if (ex == null) {
//                Log.i("Parse Result", "Successful!")
//            } else {
//                Log.i("Parse Result", "Failed$ex")
//                Log.i("stacktrace:",ex.printStackTrace().toString())
//            }
//        }


//        ParseUser.enableAutomaticUser()
//        val defaultACL = ParseACL()
//        defaultACL.publicReadAccess = true
//        defaultACL.publicWriteAccess = true
//        ParseACL.setDefaultACL(defaultACL, true)
    }
}