package com.akhbulatov.vcontachim.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.adapters.PhotosAdapter
import com.akhbulatov.vcontachim.databinding.FragmentPhotosBinding
import com.akhbulatov.vcontachim.model.PhotosAlbums
import com.akhbulatov.vcontachim.viewmodel.PhotosViewModel
import com.google.android.material.snackbar.Snackbar

class PhotosFragment : Fragment(R.layout.fragment_photos) {
    private var binding: FragmentPhotosBinding? = null
    private val viewModel by lazy {
        ViewModelProvider(this)[PhotosViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPhotosBinding.bind(view)

        binding!!.toolbar.setNavigationOnClickListener {
            VcontachimApplication.router.exit()
        }

        val photosAdapter = PhotosAdapter()
        binding!!.photos.adapter = photosAdapter

        viewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            if (it) {
                binding!!.progressBar.visibility = View.VISIBLE
            } else {
                binding!!.progressBar.visibility = View.GONE
            }
        }

        viewModel.photosLiveData.observe(viewLifecycleOwner) {
            photosAdapter.submitList(it.response.items)
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val snackbar = Snackbar.make(
                requireView(),
                it,
                Snackbar.LENGTH_LONG
            )
            snackbar.show()
        }
        val id = requireArguments().getLong(ARGUMENTS_ID)
        val title = requireArguments().getString(ARGUMENTS_TITLE)
        val sizePhoto = requireArguments().getInt(ARGUMENTS_SIZE_PHOTO)

        val plurals = VcontachimApplication.context.resources.getQuantityString(
            R.plurals.plurals_photo_albums,
            sizePhoto
        )
        binding!!.toolbar.title = title
        binding!!.toolbar.subtitle = "$sizePhoto $plurals"

        viewModel.getPhotos(id)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        private const val ARGUMENTS_ID = "id"
        private const val ARGUMENTS_SIZE_PHOTO = "sizePhoto"
        private const val ARGUMENTS_TITLE = "title"

        fun createFragment(albumId: PhotosAlbums.Items): Fragment {
            val photo = PhotosFragment()
            val bundle = Bundle()
            bundle.putLong(ARGUMENTS_ID, albumId.id)
            bundle.putInt(ARGUMENTS_SIZE_PHOTO, albumId.sizePhoto)
            bundle.putString(ARGUMENTS_TITLE, albumId.title)
            photo.arguments = bundle

            return photo
        }
    }
}