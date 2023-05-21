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

    fun preparePlayer(item: Video.Item) {
        exoPlayer = ExoPlayer.Builder(requireContext()).build()
        exoPlayer?.playWhenReady = true
        binding!!.video.player = exoPlayer
        val defaultHttpDataSourceFactory = DefaultHttpDataSource.Factory()
        val mediaItem = MediaItem.fromUri(URL)
        val mediaSource =
            DashMediaSource.Factory(defaultHttpDataSourceFactory).createMediaSource(mediaItem)
        exoPlayer?.setMediaSource(mediaSource)
        exoPlayer?.seekTo(playbackPosition)
        exoPlayer?.playWhenReady = playWhenReady
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

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    companion object {
        const val URL2 =
            "https://vk.com/video_ext.php?oid=-56643473&id=456245378&hash=b5e979a9793c2e6d&__ref=vk.api&api_hash=1684676843b4a0a093a97f38ef99_G44TKNJZGI4TGOI"
        const val URL =
            "https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd"
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