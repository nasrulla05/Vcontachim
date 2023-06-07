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

        val id = arguments?.getLong(ARGUMENTS_USER) as Long

        binding!!.exit.setOnClickListener {
            VcontachimApplication.router.exit()
        }

        viewModel.infoProfileLiveData.observe(viewLifecycleOwner) {

            if (it.online == 1L) {
                binding!!.onlineOrOffline.setImageResource(R.drawable.online_composite_16)
            } else {
                binding!!.onlineOrOffline.setImageResource(R.drawable.ic_android_black_24dp)
            }

            if (id == it.id) binding!!.subscribeOrAddFriend.visibility = View.GONE

            if (it.canSendFriendRequest == 1L) {
                binding!!.verified.setImageResource(R.drawable.ic_verified)
                binding!!.subscribeOrAddFriend.text = R.string.subscribe.toString()
                binding!!.subscribeOrAddFriend.setIconResource(R.drawable.add_square_outline_16)
                binding!!.followersOrFriends.text = it.followersCount.toString()
                binding!!.subscribeOrAddFriend.setOnClickListener {
                    binding!!.subscribeOrAddFriend.background = R.color.white.toDrawable()
                    binding!!.subscribeOrAddFriend.setTextColor(R.color.blue)
                    binding!!.subscribeOrAddFriend.text = R.string.you_subscribed.toString()
                }
            } else {
                binding!!.subscribeOrAddFriend.text = R.string.add_friend.toString()
                binding!!.subscribeOrAddFriend.setIconResource(R.drawable.user_add_outline_20)
                binding!!.subscribeOrAddFriend.setOnClickListener {
                    binding!!.subscribeOrAddFriend.background = R.color.white.toDrawable()
                    binding!!.subscribeOrAddFriend.setTextColor(R.color.blue)
                    binding!!.subscribeOrAddFriend.text = R.string.in_friends.toString()
                }

            }

            Glide.with(view)
                .load(it.photo100)
                .into(binding!!.avatar)

            binding!!.nameSurname.text = "${it.firstName} ${it.lastName}"
            binding!!.status.text = it.status
            binding!!.city.text = it.city.title
            binding!!.career.text = it.career[0].company
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val toast = Toast.makeText(
                requireContext(),
                it,
                Toast.LENGTH_LONG
            )
            toast.show()
        }

        viewModel.loadInfoProfile(id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {

        const val ARGUMENTS_USER = "USER"

        fun createFragment(id: Long): Fragment {
            val fr = InfoProfileFragment()
            val bundle = Bundle()

            bundle.putSerializable(ARGUMENTS_USER, id)

            fr.arguments = bundle
            return fr
        }
    }
}