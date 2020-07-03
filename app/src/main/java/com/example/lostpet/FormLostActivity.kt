package com.example.lostpet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lostpet.fragment.FormLostFragment

class FormLostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_lost)

        val formLostFragment = FormLostFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentLostPlaceHolder, formLostFragment)
            .commitAllowingStateLoss()
    }
}