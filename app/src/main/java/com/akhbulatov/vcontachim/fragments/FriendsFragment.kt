package com.akhbulatov.vcontachim.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.Screens
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.adapters.FriendsAdapter
import com.akhbulatov.vcontachim.databinding.FragmentFriendsBinding
import com.akhbulatov.vcontachim.model.Friends
import com.akhbulatov.vcontachim.viewmodel.FriendsViewModel
import com.google.android.material.snackbar.Snackbar

class FriendsFragment : Fragment(R.layout.fragment_friends) {
    private var binding: FragmentFriendsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFriendsBinding.bind(view)
        val viewModel: FriendsViewModel by lazy {
            ViewModelProvider(this)[FriendsViewModel::class.java]
        }

        binding!!.toolbar.setNavigationOnClickListener { VcontachimApplication.router.exit() }

        val friendAdapter = FriendsAdapter(
            object : FriendsAdapter.ClickOfAvatar {
                override fun click(friends: Friends.Item) {
                    VcontachimApplication.router.navigateTo(Screens.infoProfileFr(friends))
                }
            }
        )

        binding!!.friendsList.adapter = friendAdapter

        viewModel.friendsLiveData.observe(viewLifecycleOwner) {
            friendAdapter.submitList(it.response.items)
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