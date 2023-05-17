package com.akhbulatov.vcontachim.adddialog

import android.content.Context
import android.os.Bundle
import android.view.View
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.databinding.BottomVideoRemoveVideoBinding
import com.akhbulatov.vcontachim.model.Video
import com.google.android.material.bottomsheet.BottomSheetDialog

class RemoveVideoBottomDialog(
    context: Context,
    private val videoDel: AddVideoDeleteCallback,
    private val video: Video.Item
) : BottomSheetDialog(context) {
    private var binding: BottomVideoRemoveVideoBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bottom_video_remove_video)
        val rootLayout: View = findViewById(R.id.linearLayout)!!
        binding = BottomVideoRemoveVideoBinding.bind(rootLayout)

        binding!!.nameVideo.text = video.title
        binding!!.deleteVideo.setOnClickListener {
            videoDel.onVideoDelete(video)
            dismiss()
        }
    }

    interface AddVideoDeleteCallback {
        fun onVideoDelete(video: Video.Item)
    }
}

