package com.example.lostpet

import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
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
    }
}