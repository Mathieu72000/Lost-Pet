package com.example.lostpet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lostpet.fragment.FormFragment

class FormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val formFragment = FormFragment.newInstance(intent.getBooleanExtra(Constants.IS_LOST, false))
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.form_placeholder, formFragment)
            .commitAllowingStateLoss()
    }
}
