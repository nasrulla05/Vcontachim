package com.akhbulatov.vcontachim.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.Screens
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.databinding.FragmentInfoProfileBinding
import com.akhbulatov.vcontachim.utility.showToast
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

        binding!!.exit.setOnClickListener {
            VcontachimApplication.router.exit()
        }

        val id = arguments?.getLong(ARGUMENTS_USER)

        viewModel.infoProfileLiveData.observe(viewLifecycleOwner) { user ->
            val careerLast = user.career?.lastOrNull()

            binding!!.apply {

                onlineOrOffline.apply {
                    if (user.online == 1L) setImageResource(R.drawable.group_11)
                    else setImageResource(R.drawable.ic_android_black_24dp)
                }

                if (user.verified == 1L) verified.setImageResource(R.drawable.ic_verified)

                Glide.with(view)
                    .load(user.photo100)
                    .into(avatar)

                avatar.setOnClickListener {
                    VcontachimApplication.router.navigateTo(Screens.infoProfile(user.id))
                }

                nameSurname.text = "${user.firstName} ${user.lastName}"
                status.text = user.status
                city.text = user.city?.title

                val career2 = careerLast?.company ?: careerLast?.position
                career.text = career2

                subscribeOrAddFriend.setOnClickListener {
                    if (user.isFriend == 0) viewModel.addFriend(user.id)
                    else viewModel.deleteFriend(user.id)
                }

                subscribeOrAddFriend.apply {
                    visibility = if (user.id == 0L) View.GONE else View.VISIBLE

                    if (user.isFriend == 0) {
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
                    } else {
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
                followersOrFriends.text = "${user.counters.friends} $numberOfFriends"
            }
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            showToast(it)
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

            bundle.putLong(ARGUMENTS_USER, id)

            fr.arguments = bundle
            return fr
        }
    }
}