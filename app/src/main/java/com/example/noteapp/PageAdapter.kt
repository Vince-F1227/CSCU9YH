package com.example.noteapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.Date

class PageAdapter(fa: FragmentActivity, private val mNumOfTabs: Int) :
    FragmentStateAdapter(fa) {

    override fun getItemCount(): Int {
        return mNumOfTabs
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DateFragment()
            1 -> Page1Fragment()
            else -> Page2Fragment()
        }
    }

}