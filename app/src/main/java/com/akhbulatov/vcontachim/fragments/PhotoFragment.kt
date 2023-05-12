package com.akhbulatov.vcontachim.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.databinding.FragmentPhotoBinding
import com.akhbulatov.vcontachim.model.Item
import com.akhbulatov.vcontachim.viewmodel.PhotoViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable

class PhotoFragment : Fragment(R.layout.fragment_photo) {
    private var binding: FragmentPhotoBinding? = null
    private val viewModel by lazy {
        ViewModelProvider(this)[PhotoViewModel::class.java]
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPhotoBinding.bind(view)

        binding!!.toolbar.setNavigationOnClickListener {
            VcontachimApplication.router.exit()
        }

        val photoSerializable: Serializable? = arguments?.getSerializable(ARGUMENTS_PHOTO)
        val photo: Item? = photoSerializable as? Item

        if (photo != null) {
            binding!!.like.text = photo.likes.count.toString()
            binding!!.comments.text = photo.comments.count.toString()
            binding!!.reposts.text = photo.reposts.count.toString()
            Glide.with(view)
                .load(photo.sizes[0].photo)
                .into(binding!!.photo)

            if (photo.likes.userLikes >= 1) {
                binding!!.likes.setImageResource(R.drawable.like_filled_red_28)
            }
        }


        binding!!.likesLayout.setOnClickListener { viewModel.addLike(photo!!.id) }

        viewModel.likeLiveData.observe(viewLifecycleOwner) {
            if (photo!!.likes.userLikes < 1) {
                binding!!.like.text = "${photo.likes.count + 1}"
                binding!!.likes.setImageResource(R.drawable.like_filled_red_28)
            }
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val snackbar = Snackbar.make(
                requireView(),
                it,
                Snackbar.LENGTH_LONG
            )
            snackbar.show()
        }
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