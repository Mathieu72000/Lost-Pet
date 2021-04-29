package com.example.lostpet.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lostpet.MainActivity
import com.example.lostpet.R
import com.example.lostpet.utils.Constants
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (isUserAlreadyLogged()) {
            this.startActivityIfAlreadyLogged()
        }
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initVideoView()

        google_login.setOnClickListener {
            if (isUserAlreadyLogged()) {
                startActivity(Intent(context, MainActivity::class.java))
            } else {
                this.startSignInForGoogle()
            }
        }

        facebook_login.setOnClickListener {
            if (isUserAlreadyLogged()) {
                startActivity(Intent(context, MainActivity::class.java))
            } else {
                this.startSignInForFacebook()
            }
        }
    }


    override fun onResume() {
        super.onResume()
        cat_view.start()
        if (isUserAlreadyLogged()) {
            startActivity(Intent(context, MainActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (cat_view != null) {
            cat_view.stopPlayback()
        }
    }

    private fun startSignInForGoogle() {
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(
                    listOf(
                        AuthUI.IdpConfig.GoogleBuilder().build()
                    )
                )
                .setIsSmartLockEnabled(false, true)
                .build(), Constants.RC_SIGN_IN
        )
    }

    private fun startSignInForFacebook() {
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(
                    listOf(
                        AuthUI.IdpConfig.FacebookBuilder().build()
                    )
                )
                .setIsSmartLockEnabled(false, true)
                .build(), Constants.RC_SIGN_IN
        )
    }


    private fun initVideoView() {
        val uri = Uri.parse("android.resource://com.example.lostpet/${R.raw.cat_in_the_sun}")
        cat_view.setVideoURI(uri)
        cat_view.start()
        cat_view.setOnPreparedListener {
            it.setVolume(0f, 0f)
        }
        cat_view.setOnCompletionListener {
            cat_view.start()
        }
    }

    private fun startActivityIfAlreadyLogged() {
        startActivity(Intent(context, MainActivity::class.java))
    }

    private fun getCurrentUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

    private fun isUserAlreadyLogged(): Boolean {
        return (getCurrentUser() != null)
    }
}

