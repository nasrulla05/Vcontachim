package com.akhbulatov.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.model.PhotoComments
import com.akhbulatov.vcontachim.model.PhotoCommentsUi
import kotlinx.coroutines.launch

class PhotoCommentsViewModel : ViewModel() {
    val errorLiveData = MutableLiveData<String>()
    val commentsLiveData = MutableLiveData<List<PhotoCommentsUi>>()

    fun getComments(photoId: Long) {
        viewModelScope.launch {
            try {
                val comments: PhotoComments =
                    VcontachimApplication.vcontachimService.getComments(photoId = photoId)
                // List<PhotoComments.Items> -> List<PhotoCommentsUi>
                val photoCommUi: List<PhotoCommentsUi> = comments.response.items.map {
                    val profiles: List<PhotoComments.Profile> = comments.response.profiles
                    val response: Long = comments.response.count
                    val item: PhotoComments.Profile = profiles.first { profile ->
                        if (profile.id == it.fromId) true else false
                    }

                    val ui = PhotoCommentsUi(
                        id = it.id,
                        firstName = item.firstName,
                        lastName = item.lastName,
                        photo = item.photo,
                        textComm = it.textComments,
                        date = it.date,
                        count = response,
                        online = item.online
                    )
                    ui
                }

                commentsLiveData.value = photoCommUi
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }
}