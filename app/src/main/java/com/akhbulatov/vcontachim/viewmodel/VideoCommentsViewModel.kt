package com.akhbulatov.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.model.VideoComments
import com.akhbulatov.vcontachim.model.VideoCommentsUI
import kotlinx.coroutines.launch

class VideoCommentsViewModel : ViewModel() {

    val errorLiveData = MutableLiveData<String>()
    val videoCommLiveData = MutableLiveData<List<VideoCommentsUI>>()

    fun loadVideoComments(item:Long) {
        viewModelScope.launch {
            try {
           val videoComm:VideoComments =
               VcontachimApplication.vcontachimService.getVideoComments(item,item)

                val videoCommUI = videoComm.response.items.map {
                    val profile:List<VideoComments.Profile> = videoComm.response.profiles
                    val item:VideoComments.Profile = profile.first{ profile ->
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