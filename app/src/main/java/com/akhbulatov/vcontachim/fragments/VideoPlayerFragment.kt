package com.akhbulatov.vcontachim.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.databinding.FragmentVideoPlayerBinding
import com.akhbulatov.vcontachim.model.Video
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import java.io.Serializable
import java.text.SimpleDateFormat

class VideoPlayerFragment : Fragment(R.layout.fragment_video_player) {
    private var binding: FragmentVideoPlayerBinding? = null
    private var exoPlayer: ExoPlayer? = null
    private var playbackPosition = 0L
    private var playWhenReady = true

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVideoPlayerBinding.bind(view)

        binding!!.exit.setNavigationOnClickListener {
            VcontachimApplication.router.exit()
        }

        val itemSerializable: Serializable = arguments?.getSerializable(ARGUMENTS_ITEM)!!
        val item: Video.Item = itemSerializable as Video.Item

        binding!!.title.text = item.title
        val formatter = SimpleDateFormat("d MMMM yyyy")
        val date = formatter.format(item.date * 1000)
        binding!!.date.text = date

        val plurals = VcontachimApplication.context.resources.getQuantityString(
            R.plurals.plurals_video,
            item.views
        )
        binding!!.numberViews.text = "${item.views} $plurals"
        binding!!.commentCount.text = item.comments.toString()
        binding!!.likesCount.text = item.likes.likes_count.toString()
        binding!!.shareCount.text = item.reposts.reposts_count.toString()

        preparePlayer(item)
    }

    private fun preparePlayer(item: Video.Item) {
        exoPlayer = ExoPlayer.Builder(requireContext()).build()
        exoPlayer?.playWhenReady = true
        binding!!.video.player = exoPlayer
        val defaultHttpDataSourceFactory = DefaultHttpDataSource.Factory()
        val mediaItem = MediaItem.fromUri(item.player)
        val mediaSource =
            DashMediaSource.Factory(defaultHttpDataSourceFactory).createMediaSource(mediaItem)
        exoPlayer?.setMediaSource(mediaSource)
        exoPlayer?.seekTo(playbackPosition)
        exoPlayer?.pause()
        exoPlayer?.prepare()

    }

    private fun releasePlayer() {
        exoPlayer?.let { player ->
            playbackPosition = player.currentPosition
            playWhenReady = player.playWhenReady
            player.release()
            exoPlayer = null
        }
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
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