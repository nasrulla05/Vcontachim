package com.akhbulatov.vcontachim.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.adapters.FriendsAdapter
import com.akhbulatov.vcontachim.databinding.FragmentFriendsBinding
import com.akhbulatov.vcontachim.viewmodel.FriendsViewModel
import com.google.android.material.snackbar.Snackbar

class FriendsFragment : Fragment(R.layout.fragment_friends) {
    private var binding: FragmentFriendsBinding? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFriendsBinding.bind(view)
        val viewModel: FriendsViewModel by lazy {
            ViewModelProvider(this)[FriendsViewModel::class.java]
        }

        binding!!.toolbar.setNavigationOnClickListener { VcontachimApplication.router.exit() }

        val friendAdapter = FriendsAdapter()
        binding!!.friendsList.adapter = friendAdapter

        val dividerFriends = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding!!.friendsList.addItemDecoration(dividerFriends)

        viewModel.friendsLiveData.observe(viewLifecycleOwner) {
            friendAdapter.friends = it.response.items
            friendAdapter.notifyDataSetChanged()
        }

        viewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            if (it) {
                binding!!.progressBar.visibility = View.VISIBLE
            } else {
                binding!!.progressBar.visibility = View.GONE
            }
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val snackbar: Snackbar = Snackbar.make(
                requireView(),
                it,
                Snackbar.LENGTH_LONG
            )
            snackbar.show()
        }

        viewModel.main()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}