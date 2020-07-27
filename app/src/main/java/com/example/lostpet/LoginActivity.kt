package com.example.lostpet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lostpet.fragment.LoginFragment

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.login_placeholder, LoginFragment.newInstance())
            .commitAllowingStateLoss()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
