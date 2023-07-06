package com.akhbulatov.vcontachim.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.databinding.FragmentInfoProfileBinding
import com.akhbulatov.vcontachim.viewmodel.InfoProfileViewModel
import com.bumptech.glide.Glide

class InfoProfileFragment : Fragment(R.layout.fragment_info_profile) {
    private var binding: FragmentInfoProfileBinding? = null
    private val viewModel by lazy {
        ViewModelProvider(this)[InfoProfileViewModel::class.java]
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInfoProfileBinding.bind(view)

        val id = arguments?.getLong(ARGUMENTS_USER)

        binding!!.exit.setOnClickListener {
            VcontachimApplication.router.exit()
        }

        viewModel.infoProfileLiveData.observe(viewLifecycleOwner) { user ->

            if (user.online == 1L) {
                binding!!.onlineOrOffline.setImageResource(R.drawable.online_composite_16)
            } else {
                binding!!.onlineOrOffline.setImageResource(R.drawable.ic_android_black_24dp)
            }

            if (user.verified == 1L) {
                binding!!.verified.setImageResource(R.drawable.ic_verified)
            }

            Glide.with(view)
                .load(user.photo100)
                .into(binding!!.avatar)

            binding!!.nameSurname.text = "${user.firstName} ${user.lastName}"
            binding!!.status.text = user.status
            binding!!.city.text = user.city?.title
            binding!!.career.text = user.career?.get(0)?.position

            val plurals = user.followersCount?.toInt()?.let {
                VcontachimApplication.context.resources.getQuantityString(
                    R.plurals.followers_count,
                    it
                )
            }

            binding!!.followersOrFriends.text = "${user.followersCount} $plurals"


            binding!!.subscribeOrAddFriend.setOnClickListener {
                if (user.isFriend == 0) viewModel.addFriend(user.id)
                else viewModel.deleteFriend(user.id)
            }

            if (user.isFriend == 0) {
                binding!!.subscribeOrAddFriend.setIconResource(R.drawable.add_square_outline_16)
                binding!!.subscribeOrAddFriend.setText(R.string.add_friend)
                binding!!.subscribeOrAddFriend.setTextColor(Color.parseColor("#FFFFFFFF"))
                binding!!.subscribeOrAddFriend.background.setTint(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.blue
                    )
                )
            } else {
                binding!!.subscribeOrAddFriend.setIconResource(R.drawable.ic_verified)
                binding!!.subscribeOrAddFriend.setIconTintResource(R.color.blue)
                binding!!.subscribeOrAddFriend.setText(R.string.in_friends)
                binding!!.subscribeOrAddFriend.setTextColor(Color.parseColor("#0077FF"))
                binding!!.subscribeOrAddFriend.background.setTint(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
            }
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val toast = Toast.makeText(
                requireContext(),
                it,
                Toast.LENGTH_LONG
            )
            toast.show()
        }

        viewModel.loadInfoProfile(id!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {

        private const val ARGUMENTS_USER = "USER"

        fun createFragment(id: Long): Fragment {
            val fr = InfoProfileFragment()
            val bundle = Bundle()

            bundle.putSerializable(ARGUMENTS_USER, id)

            fr.arguments = bundle
            return fr
        }
    }
}