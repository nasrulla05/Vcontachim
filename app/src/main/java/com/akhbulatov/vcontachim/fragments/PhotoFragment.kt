package com.akhbulatov.vcontachim.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.databinding.FragmentPhotoBinding
import com.akhbulatov.vcontachim.model.Item
import com.bumptech.glide.Glide
import java.io.Serializable

class PhotoFragment : Fragment(R.layout.fragment_photo) {
    private var binding: FragmentPhotoBinding? = null

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