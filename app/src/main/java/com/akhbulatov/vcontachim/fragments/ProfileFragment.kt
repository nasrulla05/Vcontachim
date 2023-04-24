package com.akhbulatov.vcontachim.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.databinding.FragmentProfileBinding
import com.akhbulatov.vcontachim.viewmodel.ProfileViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var binding: FragmentProfileBinding? = null
    val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this)[ProfileViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        viewModel.profileLiveData.observe(viewLifecycleOwner) {
            Glide.with(this)
                .load(it.response[0].avatar)
                .into(binding!!.avatar)

            binding!!.name.text = it.response[0].name
            binding!!.surname.text = it.response[0].surname
            binding!!.mobilePhone.text = it.response[0].mobile_phone
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
        }
    }
