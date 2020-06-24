package com.example.lostpet

import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MyApplication : MultiDexApplication() {


    override fun onCreate() {
        startKoin { androidContext(this@MyApplication) }
        super.onCreate()
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
        Stetho.initializeWithDefaults(this@MyApplication)
        Places.initialize(getApplicationContext(), "AIzaSyCAu-j6HS-o21TYFVOH1ks9lu91AX843Ds")
        Places.createClient(this)
    }
}