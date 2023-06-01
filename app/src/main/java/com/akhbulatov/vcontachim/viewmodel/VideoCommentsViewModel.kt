package com.akhbulatov.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.model.Video
import com.akhbulatov.vcontachim.model.VideoComments
import com.akhbulatov.vcontachim.model.VideoCommentsUI
import kotlinx.coroutines.launch

@Suppress("NAME_SHADOWING")
class VideoCommentsViewModel : ViewModel() {

    val errorLiveData = MutableLiveData<String>()
    val videoCommLiveData = MutableLiveData<List<VideoCommentsUI>>()

    fun loadVideoComments(video: Video.Item) {
        viewModelScope.launch {
            try {
                val videoComm =
                    VcontachimApplication.vcontachimService.getVideoComments(
                        videoId = video.itemId.toLong(),
                        ownerId = video.ownerId.toLong()
                    )

                val videoCommUI = videoComm.response.items.map {
                    val profile: List<VideoComments.Profile> = videoComm.response.profiles
                    val item: VideoComments.Profile = profile.first { profile ->
                        profile.id == it.fromId
                    }

                    val ui = VideoCommentsUI(
                        id = item.id,
                        firstName = item.firsName,
                        surName = item.lastName,
                        count = it.likes.count,
                        userLikes = it.likes.userLikes,
                        fromId = it.fromId,
                        date = it.date,
                        text = it.text,
                    )
                    ui
                }
                videoCommLiveData.value = videoCommUI

            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }
}