package com.example.lostpet.viewmodel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import com.example.lostpet.LoginActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext

    fun getCurrentUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

    fun disconnectUser() {
        AuthUI.getInstance()
            .signOut(context)
    }
}