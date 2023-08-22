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
import com.akhbulatov.vcontachim.utility.showSnackbar
import com.akhbulatov.vcontachim.viewmodel.PhotosViewModel

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
            if (it) binding!!.progressBar.visibility = View.VISIBLE
            else binding!!.progressBar.visibility = View.GONE
        }

        viewModel.photosLiveData.observe(viewLifecycleOwner) {
            photosAdapter.submitList(it.response.items)
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            showSnackbar(it)
        }

        val item = requireArguments().getSerializable(ARGUMENTS_ITEM) as PhotosAlbums.Items

        val plurals = VcontachimApplication.context.resources.getQuantityString(
            R.plurals.plurals_photo_albums,
            item.sizePhoto
        )
        binding!!.toolbar.title = item.title
        binding!!.toolbar.subtitle = "${item.sizePhoto} $plurals"

        viewModel.getPhotos(id = item.id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        private const val ARGUMENTS_ITEM = "iTEM"

        fun createFragment(albumId: PhotosAlbums.Items): Fragment {
            val photo = PhotosFragment()
            val bundle = Bundle()
            bundle.putSerializable(ARGUMENTS_ITEM, albumId)

            photo.arguments = bundle
            return photo
        }
    }
}