package com.akhbulatov.vcontachim.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.adapters.PhotosAdapter
import com.akhbulatov.vcontachim.databinding.FragmentPhotoBinding
import com.akhbulatov.vcontachim.viewmodel.PhotoViewModel

class PhotoFragment : Fragment(R.layout.fragment_photo) {
    private var binding: FragmentPhotoBinding? = null
    private val viewModel by lazy {
        ViewModelProvider(this)[PhotoViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPhotoBinding.bind(view)

        binding!!.toolbarExit.setNavigationOnClickListener { VcontachimApplication.router.exit() }

        val photosAdapter = PhotosAdapter()
        binding!!.photoList.adapter = photosAdapter


    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}