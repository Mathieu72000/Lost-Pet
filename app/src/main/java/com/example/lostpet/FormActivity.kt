package com.example.lostpet

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.lostpet.fragment.FormFragment
import kotlinx.android.synthetic.main.activity_form.*

class FormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        setSupportActionBar(form_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val formFragment = FormFragment.newInstance(
            intent.getBooleanExtra(Constants.IS_LOST, false),
            intent.getStringExtra(Constants.ANIMAL_ID)
        )
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.form_placeholder, formFragment)
            .commitAllowingStateLoss()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
