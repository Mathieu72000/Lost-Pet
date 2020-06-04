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

        this.configureToolbar()

        val formFragment = FormFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.form_placeholder, formFragment)
            .commitAllowingStateLoss()
    }

    // ------------ Configuration ---------------
    private fun configureToolbar() {
        setSupportActionBar(form_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // -------- Back arrow --------
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
