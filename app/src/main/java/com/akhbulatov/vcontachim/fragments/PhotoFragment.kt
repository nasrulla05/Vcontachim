package com.akhbulatov.vcontachim.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.Screens
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.databinding.FragmentPhotoBinding
import com.akhbulatov.vcontachim.model.Item
import com.akhbulatov.vcontachim.utility.showSnackbar
import com.akhbulatov.vcontachim.viewmodel.PhotoViewModel
import com.bumptech.glide.Glide
import java.io.Serializable

class PhotoFragment : Fragment(R.layout.fragment_photo) {
    private var binding: FragmentPhotoBinding? = null
    private val viewModel: PhotoViewModel by lazy {
        ViewModelProvider(this)[PhotoViewModel::class.java]
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPhotoBinding.bind(view)

        binding!!.toolbar.setNavigationOnClickListener {
            VcontachimApplication.router.exit()
        }

        val photoSerializable: Serializable = arguments?.getSerializable(ARGUMENTS_PHOTO)!!
        var photo: Item = photoSerializable as Item

        binding!!.like.text = photo.likes.count.toString()
        binding!!.comments.text = photo.comments.count.toString()
        binding!!.reposts.text = photo.reposts.count.toString()
        Glide.with(view)
            .load(photo.sizes.lastOrNull()?.photo)
            .into(binding!!.photo)

        if (photo.likes.userLikes >= 1) {
            binding!!.likes.setImageResource(R.drawable.like_filled_red_28)
        } else {
            binding!!.likes.setImageResource(R.drawable.ic_like21)
        }

        binding!!.likesLayout.setOnClickListener {
            if (photo.likes.userLikes == 0) {
                viewModel.addLike(photo.id)
            } else {
                viewModel.deleteLike(photo.id)
            }
        }

        viewModel.likeLiveData.observe(viewLifecycleOwner) {
            photo = photo.copy(
                likes = photo.likes.copy(
                    userLikes = if (photo.likes.userLikes == 0) {
                        1
                    } else {
                        0
                    },
                    count = it.response.likes
                )
            )
            binding!!.like.text = "${photo.likes.count}"

            if (photo.likes.userLikes == 1) {
                binding!!.likes.setImageResource(R.drawable.like_filled_red_28)

            } else {
                binding!!.likes.setImageResource(R.drawable.ic_like21)
            }
        }

        viewModel.errorLikeLiveData.observe(viewLifecycleOwner) {
            showSnackbar(it)
        }

        binding!!.commentsClick.setOnClickListener {
            VcontachimApplication.router.navigateTo(Screens.commentsFr(photo))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val ARGUMENTS_PHOTO = "photo"

        fun createPhoto(itemPhoto: Item): Fragment {
            val photo = PhotoFragment()
            val bundle = Bundle()
            bundle.putSerializable(ARGUMENTS_PHOTO, itemPhoto)

            photo.arguments = bundle
            return photo
        }
    }
}