package com.akhbulatov.vcontachim.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.Screens
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.databinding.FragmentProfileBinding
import com.akhbulatov.vcontachim.model.Root
import com.akhbulatov.vcontachim.viewmodel.ProfileViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var binding: FragmentProfileBinding? = null
    private val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this)[ProfileViewModel::class.java]
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        viewModel.profileLiveData.observe(viewLifecycleOwner) {
            val response: Root.User = it.response[0]

            Glide.with(this)
                .load(response.avatar)
                .into(binding!!.avatar)

            binding!!.nameSurname.text = "${response.name} ${response.surname}"
            binding!!.mobilePhone.text = response.mobile_phone
        }

        viewModel.failureLiveData.observe(viewLifecycleOwner) {
            val error: Snackbar = Snackbar.make(
                requireView(),
                it,
                Snackbar.LENGTH_LONG
            )
            error.show()
        }
        viewModel.getProfileInfo()

        binding!!.friendsLayout.setOnClickListener {
            VcontachimApplication.router.navigateTo(Screens.friendsFr())
        }
    }
}
