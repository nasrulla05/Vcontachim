package com.akhbulatov.vcontachim.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.akhbulatov.vcontachim.fragments.NewsFragment
import com.akhbulatov.vcontachim.fragments.RecommendedFragment

class HomeAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NewsFragment()
            else -> {
                RecommendedFragment()
            }
        }
    }
}