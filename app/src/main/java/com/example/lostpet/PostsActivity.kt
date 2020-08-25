package com.example.lostpet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lostpet.fragment.PostsFragment

class PostsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.posts_placeholder, PostsFragment.newInstance())
            .commitAllowingStateLoss()
    }
}