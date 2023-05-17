package com.akhbulatov.vcontachim.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.model.Video
import kotlinx.coroutines.launch

class VideoViewModel : ViewModel() {
    val progressBarLiveData = MutableLiveData<Boolean>()
    val videoLiveData = MutableLiveData<Video>()
    val errorLiveData = MutableLiveData<String>()
    val videoDelLiveData = MutableLiveData<Video.Item>()

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

    @SuppressLint("SuspiciousIndentation")
    fun deleteVideo(videoId:Video.Item) {
        viewModelScope.launch {
            try {
                    VcontachimApplication.vcontachimService.deleteVideo(videoId = videoId.id)
                videoDelLiveData.value = videoId
            }catch (e:Exception){
                errorLiveData.value = e.message
            }
        }
    }
}