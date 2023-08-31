package com.akhbulatov.vcontachim.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.Screens
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.databinding.FragmentProfileBinding
import com.akhbulatov.vcontachim.model.Root
import com.akhbulatov.vcontachim.utility.showSnackbar
import com.akhbulatov.vcontachim.viewmodel.ProfileViewModel
import com.bumptech.glide.Glide

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var binding: FragmentProfileBinding? = null
    private val viewModel by viewModels<ProfileViewModel>()

    @SuppressLint("SetTextI18n", "ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        viewModel.profileLiveData.observe(viewLifecycleOwner) {
            val response: Root.User = it.response.last()

            Glide.with(this)
                .load(response.avatar)
                .into(binding!!.avatar)

            binding!!.nameSurname.text = "${response.name} ${response.surname}"
            binding!!.mobilePhone.text = response.mobile_phone

            binding!!.infoProfile.apply {
                setOnClickListener {
                    VcontachimApplication.router.navigateTo(Screens.infoProfile(response.id))
                }
            }
        }

        viewModel.failureLiveData.observe(viewLifecycleOwner) {
            showSnackbar(it)
        }

        viewModel.getProfileInfo()

        binding!!.apply {

            friendsLayout.setOnClickListener { VcontachimApplication.router.navigateTo(Screens.friendsFr()) }

            community.setOnClickListener { VcontachimApplication.router.navigateTo(Screens.communitiesFr()) }

            photoFragment.setOnClickListener { VcontachimApplication.router.navigateTo(Screens.photoAlbumsFr()) }

            video.setOnClickListener { VcontachimApplication.router.navigateTo(Screens.videoFr()) }

            exit.setOnClickListener { showExitDialog() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun showExitDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.logout)
        builder.setPositiveButton(R.string.exit) { _, _ ->
            val sharedPreferences: SharedPreferences =
                VcontachimApplication.context.getSharedPreferences(
                    "vcontachim_preferences",
                    Context.MODE_PRIVATE
                )
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.remove("access_token")
            editor.apply()
            VcontachimApplication.router.replaceScreen(Screens.launchAc())
        }
        builder.setNegativeButton(R.string.cansel, null)

        val exitDialog: AlertDialog = builder.create()
        exitDialog.show()
    }
}
