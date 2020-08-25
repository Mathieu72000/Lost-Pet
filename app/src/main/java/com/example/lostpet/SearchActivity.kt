package com.example.lostpet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lostpet.fragment.SearchFragment

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val searchFragment = SearchFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.search_placeHolder, searchFragment)
            .commitAllowingStateLoss()
    }
}