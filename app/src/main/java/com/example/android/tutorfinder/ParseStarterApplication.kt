package com.example.android.tutorfinder

import android.app.Application
import android.util.Log
import com.parse.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton


class ParseStarterApplication : Application(), KodeinAware {
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



//        ParseUser.enableAutomaticUser()
//        val defaultACL = ParseACL()
//        defaultACL.publicReadAccess = true
//        defaultACL.publicWriteAccess = true
//        ParseACL.setDefaultACL(defaultACL, true)
    }
    override val kodein: Kodein = Kodein.lazy {

        import(androidXModule(this@ParseStarterApplication))

//        bind() from singleton { }
    }
}