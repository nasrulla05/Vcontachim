package com.akhbulatov.vcontachim.adddialog

import android.content.Context
import android.os.Bundle
import android.view.View
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.databinding.BottomDialogVideoBinding
import com.akhbulatov.vcontachim.model.Video
import com.google.android.material.bottomsheet.BottomSheetDialog

class AddVideoBottomDialog(
    context: Context,
    private val videoDel: AddVideoDelete,
    val video: Video.Item
) :
    BottomSheetDialog(context) {
    private var binding: BottomDialogVideoBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bottom_dialog_video)
        val rootLayout: View? = findViewById(R.id.linearLayout)
        binding = rootLayout?.let { BottomDialogVideoBinding.bind(it) }

        binding!!.nameVideo.text = video.title
        binding!!.deleteVideo.setOnClickListener {
            videoDel.onVideoDelete(video)
            dismiss()
        }
    }

    interface AddVideoDelete {
        fun onVideoDelete(video: Video.Item)
    }
}

