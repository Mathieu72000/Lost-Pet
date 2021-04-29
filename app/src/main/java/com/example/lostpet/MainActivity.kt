package com.example.lostpet

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.lostpet.fragment.FoundFragment
import com.example.lostpet.fragment.LostFragment
import com.example.lostpet.utils.Constants
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val foundFragment = FoundFragment.newInstance()
    val lostFragment = LostFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(bottomAppBar)

        main_tabLayout?.apply {
            addTab(main_tabLayout.newTab().setText(getString(R.string.found)))
            addTab(main_tabLayout.newTab().setText(getString(R.string.lost)))
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tab?.position) {
                        0 -> {
                            supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.main_placeholder, foundFragment)
                                .commitAllowingStateLoss()
                        }
                        1 -> {
                            supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.main_placeholder, lostFragment)
                                .commitAllowingStateLoss()
                        }
                    }
                }
            })
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_placeholder, foundFragment)
            .commitAllowingStateLoss()

        main_fab?.setOnClickListener {
            startActivityForResult(
                Intent(this, FormSelectionActivity::class.java),
                Constants.RESULT_REQUEST_CODE
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        foundFragment.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> startActivity(Intent(this, SettingsActivity::class.java))
            R.id.search -> startActivity(Intent(this, SearchActivity::class.java))
            R.id.posts -> startActivity(Intent(this, PostsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


}
