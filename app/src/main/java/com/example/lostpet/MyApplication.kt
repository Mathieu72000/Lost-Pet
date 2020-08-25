package com.example.lostpet

import androidx.multidex.MultiDexApplication
import com.batch.android.Batch
import com.batch.android.BatchActivityLifecycleHelper
import com.batch.android.Config
import com.facebook.stetho.Stetho
import com.google.android.libraries.places.api.Places
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
        Batch.setConfig(Config("DEV5F395123900A1DD4A8CBD82F572")) // development
        registerActivityLifecycleCallbacks(BatchActivityLifecycleHelper())
        Timber.tag("batch installation id").i(Batch.User.getInstallationID())
        Places.initialize(applicationContext, "AIzaSyCAu-j6HS-o21TYFVOH1ks9lu91AX843Ds")
        Places.createClient(this)
    }
}