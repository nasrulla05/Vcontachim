package com.akhbulatov.vcontachim.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.Screens
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.adapters.VideoCommAdapter
import com.akhbulatov.vcontachim.databinding.FragmentVideoCommentsBinding
import com.akhbulatov.vcontachim.model.Video
import com.akhbulatov.vcontachim.model.VideoCommentsUI
import com.akhbulatov.vcontachim.utility.Keyboard
import com.akhbulatov.vcontachim.utility.showToast
import com.akhbulatov.vcontachim.viewmodel.VideoCommentsViewModel

class VideoCommentsFragment : Fragment(R.layout.fragment_video_comments) {
    private var binding: FragmentVideoCommentsBinding? = null
    private val viewModel by viewModels<VideoCommentsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVideoCommentsBinding.bind(view)

        binding!!.exit.setNavigationOnClickListener {
            VcontachimApplication.router.exit()
        }

        val videoCommAdapter = VideoCommAdapter(
            object : VideoCommAdapter.LikeCommListener {
                override fun addLikeComm(videoCommentsUI: VideoCommentsUI) {
                    if (videoCommentsUI.userLikes == 0L) viewModel.addLike(videoCommentsUI)
                    else viewModel.deleteLikeVideoComm(videoCommentsUI)
                }

                override fun onClick(videoCommentsUI: VideoCommentsUI) {
                    VcontachimApplication.router.navigateTo(Screens.infoProfile(videoCommentsUI.id))
                }
            })
        binding!!.commentList.adapter = videoCommAdapter

        val arg = arguments?.getSerializable(ARGUMENTS_VIDEO) as Video.Item

        binding!!.exit.subtitle = arg.comments.toString()

        viewModel.videoCommLiveData.observe(viewLifecycleOwner) {
            videoCommAdapter.submitList(it)
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            showToast(it)
        }

        viewModel.loadVideoComments(arg)

        binding!!.leaveAComment.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Вызывается ДО изменения текста
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Вызывается ВО время изменения текста
            }

            override fun afterTextChanged(s: Editable?) {
                if (binding!!.leaveAComment.text.isNotEmpty()) {
                    binding!!.submitComment.setImageResource(R.drawable.send_28_active)
                    binding!!.submitComment.setOnClickListener {
                        viewModel.createComm(arg, s!!.toString())
                        Keyboard.hideKeyBoard(view)
                        s.clear()
                    }

                } else {
                    binding!!.submitComment.setImageResource(R.drawable.send_28_not_active)
                }
            }
        })
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