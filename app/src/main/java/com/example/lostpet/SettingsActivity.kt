package com.example.lostpet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lostpet.fragment.SettingsFragment

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val settingsFragment = SettingsFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings_placeholder, settingsFragment)
            .commitAllowingStateLoss()
    }
}