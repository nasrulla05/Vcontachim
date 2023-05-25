package com.akhbulatov.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.model.LikesVideo
import kotlinx.coroutines.launch

class VideoPlayerViewModel : ViewModel() {
    val addLikeLiveData = MutableLiveData<LikesVideo>()
    val errorLiveData = MutableLiveData<String>()

    fun addLike(id: Int, ownerId: Int) {
        viewModelScope.launch {
            try {
                val like = VcontachimApplication.vcontachimService.postVideo(
                    itemId = id,
                    ownerId = ownerId
                )
                addLikeLiveData.value = like

            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }

    fun deleteLike(id: Int, ownerId: Int) {
        viewModelScope.launch {
            try {
                val deleteLike = VcontachimApplication.vcontachimService.deleteLikesVideo(
                    itemId = id,
                    ownerId = ownerId
                )
                addLikeLiveData.value = deleteLike
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }
}