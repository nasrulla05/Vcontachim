package com.akhbulatov.vcontachim.fragments

import android.annotation.SuppressLint
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
import com.akhbulatov.vcontachim.viewmodel.VideoViewModel
import com.google.android.material.snackbar.Snackbar

class VideoFragment : Fragment(R.layout.fragment_video) {
    private var binding: FragmentVideoBinding? = null
    private val viewModel by lazy {
        ViewModelProvider(this)[VideoViewModel::class.java]
    }

    @SuppressLint("NotifyDataSetChanged")
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
            if (it) {
                binding!!.progressBar.visibility = View.VISIBLE
            } else {
                binding!!.progressBar.visibility = View.GONE
            }
        }

        viewModel.videoLiveData.observe(viewLifecycleOwner) {
            videoAdapter.videos = it.response.items
            videoAdapter.notifyDataSetChanged()
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val snackbar = Snackbar.make(
                requireView(),
                it,
                Snackbar.LENGTH_LONG
            )
            snackbar.show()
        }
        viewModel.getVideo()


        viewModel.videoDelLiveData.observe(viewLifecycleOwner) {
            val mutableList = videoAdapter.videos.toMutableList()
            mutableList.remove(it)
            videoAdapter.videos = mutableList
            videoAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}