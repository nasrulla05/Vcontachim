package com.akhbulatov.vcontachim.viewmodel

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

    fun getVideo() {
        viewModelScope.launch {
            try {

                val accessToken: String? = VcontachimApplication.sharedPr.accessToken
                progressBarLiveData.value = true

                val video = VcontachimApplication.vcontachimService.getVideo("Bearer $accessToken")
                videoLiveData.value = video

                progressBarLiveData.value = false

            } catch (e: Exception) {
                progressBarLiveData.value = false
                errorLiveData.value = e.message
            }
        }
    }
}