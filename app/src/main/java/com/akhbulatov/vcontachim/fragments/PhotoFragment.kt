package com.akhbulatov.vcontachim.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.databinding.FragmentPhotoBinding
import com.akhbulatov.vcontachim.model.Photos
import com.bumptech.glide.Glide

class PhotoFragment : Fragment(R.layout.fragment_photo) {
    private var binding: FragmentPhotoBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPhotoBinding.bind(view)

        val like = requireArguments().getLong(ARGUMENTS_LIKES)
        val comments = requireArguments().getLong(ARGUMENTS_COMMENTS)
        val reposts = requireArguments().getLong(ARGUMENTS_REPOSTS)
        val photo = requireArguments().getString(ARGUMENTS_PHOTO)

        binding!!.like.text = like.toString()
        binding!!.comments.text = comments.toString()
        binding!!.reposts.text = reposts.toString()
        Glide.with(view)
            .load(photo)
            .into(binding!!.photo)
    }

    companion object {
        const val ARGUMENTS_LIKES = "likes"
        const val ARGUMENTS_COMMENTS = "comments"
        const val ARGUMENTS_REPOSTS = "reposts"
        const val ARGUMENTS_PHOTO = "photo"

        fun createPhoto(
            itemLikes: Photos.Likes,
            itemComments: Photos.Comments,
            itemReposts: Photos.Reposts,
            itemPhoto: Photos.Size
        ): Fragment {
            val photo = PhotoFragment()
            val bundle = Bundle()
            bundle.putLong(ARGUMENTS_LIKES, itemLikes.count)
            bundle.putLong(ARGUMENTS_COMMENTS, itemComments.count)
            bundle.putLong(ARGUMENTS_REPOSTS, itemReposts.count)
            bundle.putString(ARGUMENTS_PHOTO, itemPhoto.photo)
            photo.arguments = bundle

            return photo
        }
    }

}