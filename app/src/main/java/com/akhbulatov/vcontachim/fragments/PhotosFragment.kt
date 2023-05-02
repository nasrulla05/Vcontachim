package com.akhbulatov.vcontachim.fragments

import android.annotation.SuppressLint
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

    @SuppressLint("NotifyDataSetChanged")
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
            photosAdapter.photos = it
            photosAdapter.notifyDataSetChanged()
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val snackbar = Snackbar.make(
                requireView(),
                it,
                Snackbar.LENGTH_LONG
            )
            snackbar.show()
        }
        val id = requireArguments().getLong("id")
        viewModel.getPhotos(id)

    }

    companion object {
        fun main(albumId: PhotosAlbums.Items): Fragment {
            val photo = PhotosFragment()
            val bundle = Bundle()
            bundle.putLong("id", albumId.id)
            photo.arguments = bundle

            return photo
        }
    }
}