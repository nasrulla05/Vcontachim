package com.akhbulatov.vcontachim.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.adapters.VideoAdapter
import com.akhbulatov.vcontachim.adddialog.MenuVideoBottomDialog
import com.akhbulatov.vcontachim.databinding.FragmentVideoBinding
import com.akhbulatov.vcontachim.model.Video
import com.akhbulatov.vcontachim.utility.showSnackbar
import com.akhbulatov.vcontachim.viewmodel.VideoViewModel

class VideoFragment : Fragment(R.layout.fragment_video) {
    private var binding: FragmentVideoBinding? = null
    private val viewModel by lazy {
        ViewModelProvider(this)[VideoViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVideoBinding.bind(view)

        binding!!.video.setNavigationOnClickListener {
            VcontachimApplication.router.exit()
        }

        val videoAdapter = VideoAdapter(
            itemVideo = object : VideoAdapter.DeleteVideoListener {
                override fun onDeleteClick(video: Video.Item) {
                    val bottomDialog = MenuVideoBottomDialog(
                        context = view.context,
                        videoDel = object : MenuVideoBottomDialog.AddVideoDeleteCallback {
                            override fun onVideoDelete(video: Video.Item) {
                                viewModel.deleteVideo(itemVideo = video)
                            }
                        },
                        video = video
                    )
                    bottomDialog.show()
                }
            }
        )
        binding!!.videoList.adapter = videoAdapter

        viewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            if (it)binding!!.progressBar.visibility = View.VISIBLE
            else binding!!.progressBar.visibility = View.GONE
        }

        viewModel.videoLiveData.observe(viewLifecycleOwner) {
            videoAdapter.submitList(it.response.items)
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            showSnackbar(it)
        }
        viewModel.getVideo()


        viewModel.videoDelLiveData.observe(viewLifecycleOwner) {
            val mutableList = videoAdapter.currentList.toMutableList()
            mutableList.remove(it)
            videoAdapter.submitList(mutableList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}