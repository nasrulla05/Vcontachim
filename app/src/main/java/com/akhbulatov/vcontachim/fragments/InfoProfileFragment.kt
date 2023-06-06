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
import com.akhbulatov.vcontachim.model.*
import com.akhbulatov.vcontachim.viewmodel.InfoProfileViewModel
import com.bumptech.glide.Glide
import java.io.Serializable

class InfoProfileFragment : Fragment(R.layout.fragment_info_profile) {
    private var binding: FragmentInfoProfileBinding? = null
    private val viewModel by lazy {
        ViewModelProvider(this)[InfoProfileViewModel::class.java]
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInfoProfileBinding.bind(view)

//        val user = passingOnArgument(ARGUMENTS_USER) as Root.User
//        val friends = passingOnArgument(ARGUMENTS_FRIENDS) as Friends.Item
        val videoComm = passingOnArgument(ARGUMENTS_VIDEO_COMM) as VideoCommentsUI
//        val photoCommentsUi = passingOnArgument(ARGUMENTS_PHOTO_COMM) as PhotoCommentsUi
//        val player = passingOnArgument(ARGUMENTS_VIDEO_PLAYER) as Video.Item

        binding!!.exit.setOnClickListener {
            VcontachimApplication.router.exit()
        }

        viewModel.infoProfileLiveData.observe(viewLifecycleOwner) {

            if (it.online == 1L) {
                binding!!.onlineOrOffline.setImageResource(R.drawable.online_composite_16)
            } else {
                binding!!.onlineOrOffline.setImageResource(R.drawable.ic_android_black_24dp)
            }

//            if (user.id == response.id) binding!!.subscribeOrAddFriend.visibility = View.GONE

            if (it.verified == 1L) {
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
                if (it.friendStatus == 0L) {
                    binding!!.subscribeOrAddFriend.setOnClickListener {
                        binding!!.subscribeOrAddFriend.background = R.color.white.toDrawable()
                        binding!!.subscribeOrAddFriend.setTextColor(R.color.blue)
                        binding!!.subscribeOrAddFriend.text = R.string.in_friends.toString()
                    }
                }
            }

            Glide.with(view)
                .load(it.photo100)
                .into(binding!!.avatar)

            binding!!.nameSurname.text = "${it.firstName} ${it.lastName}"
            binding!!.status.text = it.status
            binding!!.city.text = it.city.title
            binding!!.career.text = it.career[0].groupId.toString()
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val toast = Toast.makeText(
                requireContext(),
                it,
                Toast.LENGTH_LONG
            )
            toast.show()
        }

        viewModel.loadInfoProfile(videoComm.id)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {

        const val ARGUMENTS_FRIENDS = "FRIENDS"
        const val ARGUMENTS_USER = "USER"
        const val ARGUMENTS_VIDEO_COMM = "VIDEO_COMM"
        const val ARGUMENTS_PHOTO_COMM = "PHOTO_COMM"
        const val ARGUMENTS_VIDEO_PLAYER = "PLAYER"

        private val fr = InfoProfileFragment()
        private val bundle = Bundle()


        fun createFragment(user: Root.User): Fragment {
            bundle.putSerializable(ARGUMENTS_USER, user)

            fr.arguments = bundle
            return fr
        }

        fun createFragmentFr(friends: Friends.Item): Fragment {
            bundle.putSerializable(ARGUMENTS_FRIENDS, friends)

            fr.arguments = bundle
            return fr
        }

        fun createFragmentVid(ui: VideoCommentsUI): Fragment {
            bundle.putSerializable(ARGUMENTS_VIDEO_COMM, ui)

            fr.arguments = bundle
            return fr
        }

        fun createFragmentPhoto(ui: PhotoCommentsUi): Fragment {
            bundle.putSerializable(ARGUMENTS_PHOTO_COMM, ui)

            fr.arguments = bundle
            return fr
        }

        fun createFragmentVidPlayer(player: Video.Item): Fragment {
            bundle.putSerializable(ARGUMENTS_VIDEO_PLAYER, player)

            fr.arguments = bundle
            return fr
        }
    }

    private fun passingOnArgument(arg: String): Serializable? {
        return arguments?.getSerializable(arg)
    }
}