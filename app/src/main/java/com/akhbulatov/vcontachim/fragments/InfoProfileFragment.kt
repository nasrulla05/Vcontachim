package com.akhbulatov.vcontachim.fragments

import Users
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

        val id = arguments?.getLong(ARGUMENTS_USER) as Long

        binding!!.exit.setOnClickListener {
            VcontachimApplication.router.exit()
        }

        viewModel.infoProfileLiveData.observe(viewLifecycleOwner) {
            val response: Users.Response = it.response[0]

            if (response.online == 1L) {
                binding!!.onlineOrOffline.setImageResource(R.drawable.online_composite_16)
            } else {
                binding!!.onlineOrOffline.setImageResource(R.drawable.ic_android_black_24dp)
            }

            if (response.verified == 1L) {
                binding!!.verified.setImageResource(R.drawable.ic_verified)
            }

            binding!!.subscribeOrAddFriend.setOnClickListener {
                if (response.isFriend == 1) {
                    viewModel.addFriend(response.id)
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
                } else {
                    viewModel.deleteFriend(response.id)
                    binding!!.subscribeOrAddFriend.setIconResource(R.drawable.add_square_outline_16)
                    binding!!.subscribeOrAddFriend.setText(R.string.add_friend)
                    binding!!.subscribeOrAddFriend.setTextColor(Color.parseColor("#FFFFFFFF"))
                    binding!!.subscribeOrAddFriend.background.setTint(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.blue
                        )
                    )
                }
            }

            Glide.with(view)
                .load(response.photo100)
                .into(binding!!.avatar)

            binding!!.nameSurname.text = "${response.firstName} ${response.lastName}"
            binding!!.status.text = response.status
            binding!!.city.text = response.city?.title
            binding!!.career.text = response.career?.get(0)?.position

            val plurals = response.followersCount?.toInt()?.let { it1 ->
                VcontachimApplication.context.resources.getQuantityString(
                    R.plurals.followers_count,
                    it1
                )
            }

            binding!!.followersOrFriends.text = "${response.followersCount} $plurals"
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

        private const val ARGUMENTS_USER = "USER"

        fun createFragment(id: Long): Fragment {
            val fr = InfoProfileFragment()
            val bundle = Bundle()

            bundle.putSerializable(ARGUMENTS_USER, id)

            fr.arguments = bundle
            return fr
        }
    }

//    if (response.friendStatus != 3) {
//        viewModel.addFriend(response.id)
//        binding!!.subscribeOrAddFriend.setIconResource(R.drawable.ic_verified)
//        binding!!.subscribeOrAddFriend.setIconTintResource(R.color.blue)
//        binding!!.subscribeOrAddFriend.setText(R.string.in_friends)
//        binding!!.subscribeOrAddFriend.setTextColor(Color.parseColor("#0077FF"))
//        binding!!.subscribeOrAddFriend.background.setTint(
//            ContextCompat.getColor(
//                requireContext(),
//                R.color.white
//            )
//        )
//    } else {
//        viewModel.deleteFriend(response.id)
//        binding!!.subscribeOrAddFriend.setIconResource(R.drawable.add_square_outline_16)
//        binding!!.subscribeOrAddFriend.setText(R.string.add_friend)
//        binding!!.subscribeOrAddFriend.setTextColor(Color.parseColor("#FFFFFFFF"))
//        binding!!.subscribeOrAddFriend.background.setTint(
//            ContextCompat.getColor(
//                requireContext(),
//                R.color.blue
//            )
//        )
//
//    }
}