package com.example.lostpet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lostpet.fragment.FormDescriptionFragment
import com.example.lostpet.utils.Constants

class FormDescriptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_description)

        val formDescriptionFragment = FormDescriptionFragment.newInstance(
            intent?.getStringExtra(
                Constants.ANIMAL_ID
            )
        )
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.formDescription_placeHolder, formDescriptionFragment)
            .commitAllowingStateLoss()
    }
}