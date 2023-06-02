package com.akhbulatov.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.model.Video
import com.akhbulatov.vcontachim.model.VideoComments
import com.akhbulatov.vcontachim.model.VideoCommentsUI
import kotlinx.coroutines.launch

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

    fun addLike(video: VideoCommentsUI) {
        viewModelScope.launch {
            try {
                VcontachimApplication.vcontachimService.addLikeCommVideo(itemId = video.id)
                val mutList = videoCommLiveData.value?.toMutableList()
                val result = video.copy(
                    userLikes = if (video.userLikes == 1L) 0 else 1
                )
                val index = mutList!!.indexOf(video)
                mutList.set(index, result)

                videoCommLiveData.value = mutList

            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }

    fun deleteLikeVideoComm(video: VideoCommentsUI) {
        viewModelScope.launch {
            try {
                VcontachimApplication.vcontachimService.deleteLikeCommVideo(itemId = video.id)
                val mutList = videoCommLiveData.value?.toMutableList()
                val result = video.copy(
                    userLikes = if (video.userLikes == 1L) 0 else 1
                )
                val index = mutList!!.indexOf(video)
                mutList[index] = result

                videoCommLiveData.value = mutList

            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }

    fun createComm(video: Video.Item, message: String) {
        viewModelScope.launch {
            try {
                VcontachimApplication.vcontachimService.submitVideoComment(
                    videoId = video.itemId.toLong(),
                    ownerId = video.ownerId.toLong(),
                    message = message
                )
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }
}