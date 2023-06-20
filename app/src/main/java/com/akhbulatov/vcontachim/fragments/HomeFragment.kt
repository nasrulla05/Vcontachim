package com.akhbulatov.vcontachim.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.adapters.HomeAdapter
import com.akhbulatov.vcontachim.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

 class HomeFragment : Fragment(R.layout.fragment_home) {
    private var binding: FragmentHomeBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        val homeAdapter = HomeAdapter(this)
        binding!!.viewPager2.adapter = homeAdapter

        val homeMediator = TabLayoutMediator(
            binding!!.tabLayout, binding!!.viewPager2
        ) { tab, position ->
            when (position) {
                0 -> tab.setText(R.string.news)
                1 -> tab.setText(R.string.recommended)
            }
        }
        homeMediator.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}