package com.example.lostpet

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.lostpet.fragment.LostPetFragment
import com.example.lostpet.fragment.MainFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*

class MainActivity : AppCompatActivity() {

    val mainFragment = MainFragment.newInstance()
    val lostPetFragment = LostPetFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(bottomAppBar)
        setSupportActionBar(main_toolbar)
        supportActionBar?.hide()

        main_tabLayout?.addTab(main_tabLayout.newTab().setText("Found"))
        main_tabLayout?.addTab(main_tabLayout.newTab().setText("Lost"))
        main_tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.main_placeholder, mainFragment)
                            .commitAllowingStateLoss()
                    }
                    1 -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.main_placeholder, lostPetFragment)
                            .commitAllowingStateLoss()
                    }
                }
            }

        })
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_placeholder, mainFragment)
            .commitAllowingStateLoss()

        main_fab?.setOnClickListener {
            startActivityForResult(
                Intent(this, FormSelectionActivity::class.java), Constants.RESULT_REQUEST_CODE
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mainFragment.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> startActivity(Intent(this, SettingsActivity::class.java))
            R.id.search -> Toast.makeText(this, "Working on it", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


}
