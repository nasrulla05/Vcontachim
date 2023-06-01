package com.akhbulatov.vcontachim.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.Screens
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.databinding.FragmentVideoPlayerBinding
import com.akhbulatov.vcontachim.model.Video
import com.akhbulatov.vcontachim.viewmodel.VideoPlayerViewModel
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
    private val viewModel: VideoPlayerViewModel by lazy {
        ViewModelProvider(this)[VideoPlayerViewModel::class.java]
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVideoPlayerBinding.bind(view)

        binding!!.exit.setNavigationOnClickListener {
            VcontachimApplication.router.exit()
        }

        val itemSerializable: Serializable = arguments?.getSerializable(ARGUMENTS_ITEM)!!
        var item: Video.Item = itemSerializable as Video.Item

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
        binding!!.likesCount.text = item.likes.likesCount.toString()
        binding!!.shareCount.text = item.reposts.reposts_count.toString()

        if (item.likes.userLikes >= 1) {
            binding!!.like.setColorFilter(
                ContextCompat.getColor(requireContext(), R.color.red),
                android.graphics.PorterDuff.Mode.MULTIPLY
            )
            binding!!.like.setImageResource(R.drawable.like_filled_red_28)
        } else {
            binding!!.like.setImageResource(R.drawable.ic_like21)
        }

        binding!!.likesClick.setOnClickListener {
            if (item.likes.userLikes == 0) {
                viewModel.addLike(
                    id = item.itemId,
                    ownerId = item.ownerId
                )
            } else {
                viewModel.deleteLike(id = item.itemId, ownerId = item.ownerId)
            }
        }

        viewModel.addLikeLiveData.observe(viewLifecycleOwner) {
            item = item.copy(
                likes = item.likes.copy(
                    userLikes = if (item.likes.userLikes == 0) 1 else 0,
                    likesCount = it.response.likes
                )
            )
            binding!!.likesCount.text = "${item.likes.likesCount}"

            if (item.likes.userLikes > 0) {
                binding!!.like.setColorFilter(
                    ContextCompat.getColor(requireContext(), R.color.red),
                    android.graphics.PorterDuff.Mode.MULTIPLY
                )
                binding!!.like.setImageResource(R.drawable.like_filled_red_28)
            } else {
                binding!!.like.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    ), android.graphics.PorterDuff.Mode.MULTIPLY
                )
                binding!!.like.setImageResource(R.drawable.ic_like21)
            }
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val toast = Toast.makeText(
                context,
                it,
                Toast.LENGTH_LONG
            )
            toast.show()
        }

        preparePlayer(item)

        binding!!.commentsClick.setOnClickListener {
            VcontachimApplication.router.navigateTo(Screens.videoCommAc(item))
        }
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