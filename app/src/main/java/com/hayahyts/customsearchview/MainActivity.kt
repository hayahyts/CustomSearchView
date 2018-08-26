package com.hayahyts.customsearchview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val suggestions = ArrayList<String>()
        suggestions.add("SpaceWek")
        suggestions.add("Google")
        suggestions.add("Tesla")
        suggestions.add("AirBnb")

        searchBar.setSuggestions(suggestions)
    }
}
