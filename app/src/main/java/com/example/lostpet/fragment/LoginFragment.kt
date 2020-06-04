package com.example.lostpet.fragment

import android.Manifest
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
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber

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
        this.requestLocationPermission()
        if (isUserAlreadyLogged()) {
            this.startActivityIfAlreadyLogged()
        }
        return super.onCreateView(inflater, container, savedInstanceState)
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @AfterPermissionGranted(Constants.REQUEST_LOCATION_PERMISSION)
    private fun requestLocationPermission() {
        if (EasyPermissions.hasPermissions(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            Timber.i("permission granted")
        } else {
            EasyPermissions.requestPermissions(
                this,
                resources.getString(R.string.rational),
                Constants.REQUEST_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    private fun getCurrentUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

    private fun isUserAlreadyLogged(): Boolean {
        return (getCurrentUser() != null)
    }
}

