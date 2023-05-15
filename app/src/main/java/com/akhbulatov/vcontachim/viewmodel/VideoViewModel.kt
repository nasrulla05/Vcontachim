package com.akhbulatov.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.model.Video
import com.akhbulatov.vcontachim.model.VideoDel
import kotlinx.coroutines.launch

class VideoViewModel : ViewModel() {
    val progressBarLiveData = MutableLiveData<Boolean>()
    val videoLiveData = MutableLiveData<Video>()
    val errorLiveData = MutableLiveData<String>()
    val videoDeleteLiveData = MutableLiveData<VideoDel>()

    fun getVideo() {
        viewModelScope.launch {
            try {
                progressBarLiveData.value = true

                val video = VcontachimApplication.vcontachimService.getVideo()
                videoLiveData.value = video

                progressBarLiveData.value = false

            } catch (e: Exception) {
                progressBarLiveData.value = false
                errorLiveData.value = e.message
            }
        }
    }

    fun deleteVideo(videoId: Long) {
        viewModelScope.launch {
            try {
                progressBarLiveData.value = true

                val deleteVideo =
                    VcontachimApplication.vcontachimService.deleteVideo(videoId = videoId)
                videoDeleteLiveData.value = deleteVideo

                progressBarLiveData.value = false
            } catch (e: Exception) {
                progressBarLiveData.value = false
                errorLiveData.value = e.message
            }
        }
    }
}