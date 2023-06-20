package com.akhbulatov.vcontachim.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.akhbulatov.vcontachim.fragments.NewsFragment
import com.akhbulatov.vcontachim.model.TypeNews

class HomeAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NewsFragment.createFragment(TypeNews.NEWS)
            else -> {
                NewsFragment.createFragment(TypeNews.RECOMMENDED)
            }
        }
    }
}