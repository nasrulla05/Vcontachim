package com.akhbulatov.vcontachim.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.adapters.CommunityAdapter
import com.akhbulatov.vcontachim.databinding.FragmentCommunitiesBinding
import com.akhbulatov.vcontachim.utility.showSnackbar
import com.akhbulatov.vcontachim.viewmodel.CommunitiesViewModel

class CommunitiesFragment : Fragment(R.layout.fragment_communities) {
    private var binding: FragmentCommunitiesBinding? = null
    private val viewModel: CommunitiesViewModel by lazy {
        ViewModelProvider(this)[CommunitiesViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCommunitiesBinding.bind(view)

        binding!!.toolbar.setNavigationOnClickListener {
            VcontachimApplication.router.exit()
        }

        val communityAdapter = CommunityAdapter()
        binding!!.communityList.adapter = communityAdapter

        viewModel.communityLiveData.observe(viewLifecycleOwner) {
            communityAdapter.submitList(it.response.items)
        }

        viewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            if (it) binding!!.progressBar.visibility = View.VISIBLE
            else binding!!.progressBar.visibility = View.GONE
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            showSnackbar(it)
        }

        viewModel.getCommunity()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}