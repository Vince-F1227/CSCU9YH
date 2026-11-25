
package com.example.noteapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        try {
            val fileInput = openFileInput("savedNotes.txt")
            val jsonText = fileInput.bufferedReader().use { it.readText() }

            val jsonArray = JSONArray(jsonText)
            val notes = mutableListOf<String>()

            for (i in 0 until jsonArray.length()) {
                notes.add(jsonArray.getString(i))
            }

            viewModel.loadNotes(notes)

        } catch (e: Exception) {
            viewModel.loadNotes(emptyList())
        }

        try {
            val date = openFileInput("savedDates.txt")
                .bufferedReader()
                .use { it.readText() }

            viewModel.saveDate(date)

        } catch (e: Exception) {
            viewModel.saveDate("")
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_page1))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_page2))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_page3))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val viewPager = findViewById<ViewPager2>(R.id.pager)
        val adapter = PageAdapter(this, 3)

        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "Page " + (position + 1)
        }.attach()
    }
}
