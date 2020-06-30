package com.example.lostpet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lostpet.fragment.FormSelectionFragment

class FormSelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_selection)

        val formSelectionFragment = FormSelectionFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.formSelectionPlaceHolder, formSelectionFragment)
            .commitAllowingStateLoss()
    }
}