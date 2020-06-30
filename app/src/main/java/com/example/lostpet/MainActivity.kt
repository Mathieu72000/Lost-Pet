package com.example.lostpet

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lostpet.fragment.MainFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(bottomAppBar)

        val mainFragment = MainFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_placeholder, mainFragment)
            .commitAllowingStateLoss()

        main_found_button?.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_placeholder, mainFragment)
                .commitAllowingStateLoss()
        }
    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.search -> Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show()
//        }
//        return super.onOptionsItemSelected(item)
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.main_menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
}
