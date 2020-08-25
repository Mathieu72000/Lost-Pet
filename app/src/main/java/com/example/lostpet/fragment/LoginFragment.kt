package com.example.lostpet.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lostpet.Constants
import com.example.lostpet.MainActivity
import com.example.lostpet.R
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
        if (isUserAlreadyLogged()) {
            startActivity(Intent(context, MainActivity::class.java))
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

