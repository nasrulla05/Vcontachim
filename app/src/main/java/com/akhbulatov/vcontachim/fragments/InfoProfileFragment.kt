package com.akhbulatov.vcontachim.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.databinding.FragmentInfoProfileBinding
import com.akhbulatov.vcontachim.model.Root
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


        val arg = arguments?.getSerializable(ARGUMENTS_USER)
        val user = arg as Root.User

        binding!!.exit.setOnClickListener {
            VcontachimApplication.router.exit()
        }

        viewModel.infoProfileLiveData.observe(viewLifecycleOwner) {
            if (it.response[0].online == 1L) {
                binding!!.onlineOrOffline.setImageResource(R.drawable.online_composite_16)
            } else {
                binding!!.onlineOrOffline.setImageResource(R.drawable.ic_android_black_24dp)
            }

            if (user.id == it.response[0].id) binding!!.subscribeOrAddFriend.visibility = View.GONE

            if (it.response[0].verified == 1L) {
                binding!!.verified.setImageResource(R.drawable.ic_verified)
                binding!!.subscribeOrAddFriend.text = R.string.subscribe.toString()
                binding!!.subscribeOrAddFriend.setIconResource(R.drawable.add_square_outline_16)
                binding!!.followersOrFriends.text = it.response[0].followersCount.toString()
                binding!!.subscribeOrAddFriend.setOnClickListener {
                    binding!!.subscribeOrAddFriend.background = R.color.white.toDrawable()
                    binding!!.subscribeOrAddFriend.setTextColor(R.color.blue)
                    binding!!.subscribeOrAddFriend.text = R.string.you_subscribed.toString()
                }
            } else {
                binding!!.subscribeOrAddFriend.text = R.string.add_friend.toString()
                binding!!.subscribeOrAddFriend.setIconResource(R.drawable.user_add_outline_20)
                if (it.response[0].friendStatus == 0L) {
                    binding!!.subscribeOrAddFriend.setOnClickListener {
                        binding!!.subscribeOrAddFriend.setTextColor(R.color.blue)
                        binding!!.subscribeOrAddFriend.text = R.string.in_friends.toString()
                    }
                }
            }

            Glide.with(view)
                .load(it.response[0].photo100)
                .into(binding!!.avatar)

            binding!!.nameSurname.text = "${it.response[0].firstName} ${it.response[0].lastName}"
            binding!!.status.text = it.response[0].status
            binding!!.city.text = it.response[0].city.title
            binding!!.career.text = it.response[0].career[0].groupId.toString()
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val toast = Toast.makeText(
                requireContext(),
                it,
                Toast.LENGTH_LONG
            )
            toast.show()
        }

        viewModel.loadInfoProfile(user)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {

        const val ARGUMENTS_USER = "USER"

        fun createFragment(user: Root.User): Fragment {
            val fr = InfoProfileFragment()
            val bundle = Bundle()
            bundle.putSerializable(ARGUMENTS_USER, user)

            fr.arguments = bundle
            return fr
        }
    }
}