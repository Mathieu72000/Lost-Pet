package com.example.lostpet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lostpet.fragment.SearchResultFragment
import com.example.lostpet.model.Animal
import com.example.lostpet.utils.Constants

class SearchResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.search_activity_placeholder, SearchResultFragment.newInstance(
                    intent.getSerializableExtra(
                        Constants.SEARCH_RESULT
                    ) as? ArrayList<Animal>?
                )
            )
            .commitAllowingStateLoss()
    }
}