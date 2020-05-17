package com.airtel.demo.search.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.airtel.demo.R
import com.airtel.demo.search.view.fragment.SearchFragment

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        attachFragment()
    }

    private fun attachFragment() {
        supportFragmentManager.beginTransaction().add(
            R.id.parent_view,
            SearchFragment()
        ).commit()
    }

}
