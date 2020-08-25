package com.example.lostpet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lostpet.fragment.SearchResultFragment
import com.example.lostpet.model.Animal
import java.io.Serializable

class SearchResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.search_activity_placeholder, SearchResultFragment.newInstance(intent.getSerializableExtra(Constants.SEARCH_RESULT) as? ArrayList<Animal>?))
            .commitAllowingStateLoss()
    }
}