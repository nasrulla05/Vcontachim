package com.akhbulatov.vcontachim.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.adapters.VideoCommAdapter
import com.akhbulatov.vcontachim.databinding.FragmentVideoCommentsBinding
import com.akhbulatov.vcontachim.viewmodel.VideoCommentsViewModel

class VideoCommentsFragment:Fragment(R.layout.fragment_video_comments) {
    private var binding:FragmentVideoCommentsBinding? = null
    private val viewModel by lazy {
        ViewModelProvider(this)[VideoCommentsViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVideoCommentsBinding.bind(view)

        val videoCommAdapter = VideoCommAdapter()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}