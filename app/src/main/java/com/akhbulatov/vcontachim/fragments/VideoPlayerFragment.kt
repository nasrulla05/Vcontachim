package com.akhbulatov.vcontachim.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.databinding.FragmentVideoPlayerBinding
import com.akhbulatov.vcontachim.model.Video
import java.io.File
import java.io.Serializable
import java.text.SimpleDateFormat

class VideoPlayerFragment : Fragment(R.layout.fragment_video_player) {
    private var binding: FragmentVideoPlayerBinding? = null

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVideoPlayerBinding.bind(view)

        binding!!.exit.setNavigationOnClickListener {
            VcontachimApplication.router.exit()
        }

        val itemSerializable: Serializable = arguments?.getSerializable(ARGUMENTS_ITEM)!!
        val item: Video.Item = itemSerializable as Video.Item
        binding!!.play.setOnClickListener {

        }
        binding!!.title.text = item.title
        val formatter = SimpleDateFormat("d MMMM yyyy")
        val date = formatter.format(item.date * 1000)
        binding!!.date.text = date
        val plurals = VcontachimApplication.context.resources.getQuantityString(R.plurals.plurals_video,item.views)
        binding!!.numberViews.text = "${item.views} $plurals"
        binding!!.commentCount.text = item.comments.toString()
        binding!!.likesCount.text = item.likes.likes_count.toString()
        binding!!.shareCount.text = item.reposts.reposts_count.toString()

        binding!!.play.setOnClickListener {
            binding!!.video.setVideoURI(File(item.player).toUri())
//            binding!!.video.setMediaController(MediaController(requireContext()))
//            binding!!.video.requestFocus()
            binding!!.video.start()
        }
    }

    companion object {
        const val ARGUMENTS_ITEM = "ITEM"

        fun createVideoPlayer(
            item: Video.Item
        ): Fragment {
            val videoPlayer = VideoPlayerFragment()
            val bundle = Bundle()
            bundle.putSerializable(ARGUMENTS_ITEM, item)
            videoPlayer.arguments = bundle

            return videoPlayer
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}