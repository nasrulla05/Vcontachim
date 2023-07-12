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
import com.akhbulatov.vcontachim.Screens
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.databinding.FragmentInfoProfileBinding
import com.akhbulatov.vcontachim.viewmodel.InfoProfileViewModel
import com.bumptech.glide.Glide

class InfoProfileFragment : Fragment(R.layout.fragment_info_profile) {
    private var binding: FragmentInfoProfileBinding? = null
    private val viewModel by lazy {
        ViewModelProvider(this)[InfoProfileViewModel::class.java]
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInfoProfileBinding.bind(view)

        val id = arguments?.getLong(ARGUMENTS_USER)

        binding!!.exit.setOnClickListener {
            VcontachimApplication.router.exit()
        }

        viewModel.infoProfileLiveData.observe(viewLifecycleOwner) { user ->

            binding!!.onlineOrOffline.apply {
                if (user.online == 1L) setImageResource(R.drawable.group_11)
                 else setImageResource(R.drawable.ic_android_black_24dp)
            }

            if (user.verified == 1L) {
                binding!!.verified.setImageResource(R.drawable.ic_verified)
            }

            Glide.with(view)
                .load(user.photo100)
                .into(binding!!.avatar)

            binding!!.avatar.setOnClickListener {
                VcontachimApplication.router.navigateTo(Screens.infoProfile(user.id))
            }

            binding!!.nameSurname.text = "${user.firstName} ${user.lastName}"
            binding!!.status.text = user.status
            binding!!.city.text = user.city?.title

            val career = if (user.career?.lastOrNull()?.position == null) {
                user.career?.lastOrNull()?.company
            } else {
                user.career.lastOrNull()?.position
            }

            binding!!.career.text = career

            binding!!.subscribeOrAddFriend.setOnClickListener {
                if (user.isFriend == 0) viewModel.addFriend(user.id)
                else viewModel.deleteFriend(user.id)
            }

                if (user.isFriend == 0) {
                    binding!!.subscribeOrAddFriend.apply {
                        setIconResource(R.drawable.user_add_outline_20)
                        setIconTintResource(R.color.white)
                        setText(R.string.add_friend)
                        setTextColor(Color.parseColor("#FFFFFFFF"))
                        background.setTint(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.blue
                            )
                        )

                    }
                }
                else {
                    binding!!.subscribeOrAddFriend.apply {
                        setIconResource(R.drawable.ic_verified)
                        setIconTintResource(R.color.blue)
                        setText(R.string.in_friends)
                        setTextColor(Color.parseColor("#0077FF"))
                        background.setTint(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.white
                            )
                        )
                    }
                }

                val numberOfFriends = VcontachimApplication.context.resources.getQuantityString(
                    R.plurals.friends_count,
                    user.counters.friends
                )
                binding!!.followersOrFriends.text = "${user.counters.friends} $numberOfFriends"
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