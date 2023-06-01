package com.akhbulatov.vcontachim.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.adapters.VideoCommAdapter
import com.akhbulatov.vcontachim.databinding.FragmentVideoCommentsBinding
import com.akhbulatov.vcontachim.model.Video
import com.akhbulatov.vcontachim.viewmodel.VideoCommentsViewModel

class VideoCommentsFragment : Fragment(R.layout.fragment_video_comments) {
    private var binding: FragmentVideoCommentsBinding? = null
    private val viewModel by lazy {
        ViewModelProvider(this)[VideoCommentsViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVideoCommentsBinding.bind(view)

        binding!!.exit.setNavigationOnClickListener {
            VcontachimApplication.router.exit()
        }

        val videoCommAdapter = VideoCommAdapter()
        binding!!.commentList.adapter = videoCommAdapter

        val arg = arguments?.getSerializable(ARGUMENTS_VIDEO)!!
        val video: Video.Item = arg as Video.Item

        binding!!.exit.subtitle = video.comments.toString()

        viewModel.videoCommLiveData.observe(viewLifecycleOwner) {
            videoCommAdapter.submitList(it)
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val toast = Toast.makeText(
                requireContext(),
                it,
                Toast.LENGTH_LONG
            )
            toast.show()
        }

        viewModel.loadVideoComments(video)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        private const val ARGUMENTS_VIDEO = "VIDEO"

        fun createFragment(video: Video.Item): Fragment {
            val fr = VideoCommentsFragment()
            val bundle = Bundle()
            bundle.putSerializable(ARGUMENTS_VIDEO, video)
            fr.arguments = bundle

            return fr
        }
    }
}