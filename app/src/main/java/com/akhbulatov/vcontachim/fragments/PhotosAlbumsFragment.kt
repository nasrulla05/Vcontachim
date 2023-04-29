package com.akhbulatov.vcontachim.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.adapters.PhotoAlbumsAdapter
import com.akhbulatov.vcontachim.databinding.FragmentPhotoBinding
import com.akhbulatov.vcontachim.viewmodel.PhotoAlbumsViewModel
import com.google.android.material.snackbar.Snackbar

class PhotosAlbumsFragment : Fragment(R.layout.fragment_photo_albums) {
    private var binding: FragmentPhotoBinding? = null
    private val viewModel by lazy {
        ViewModelProvider(this)[PhotoAlbumsViewModel::class.java]
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPhotoBinding.bind(view)

        binding!!.toolbarExit.setNavigationOnClickListener { VcontachimApplication.router.exit() }

        val photosAdapter = PhotoAlbumsAdapter()
        binding!!.photoList.adapter = photosAdapter

        viewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            if (it) {
                binding!!.progressBar.visibility = View.VISIBLE
            } else {
                binding!!.progressBar.visibility = View.GONE
            }
        }

        viewModel.photoAlbumsLiveData.observe(viewLifecycleOwner) {
            photosAdapter.photos = it.response.items
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
        viewModel.getPhotoAlbums()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}