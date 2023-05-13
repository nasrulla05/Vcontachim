package com.akhbulatov.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.model.Like
import kotlinx.coroutines.launch

class PhotoViewModel : ViewModel() {
    val likeLiveData = MutableLiveData<Like>()
    val errorLikeLiveData = MutableLiveData<String>()

    fun addLike(photoId: Long) {
        viewModelScope.launch {
            try {

                val token = VcontachimApplication.sharedPr.accessToken
                val like = VcontachimApplication.vcontachimService.postLike(
                    itemId = photoId,
                    token = "Bearer $token"
                )
                likeLiveData.value = like

            } catch (e: Exception) {
                errorLikeLiveData.value = e.message
            }
        }
    }

    fun deleteLike(photoId: Long) {
        viewModelScope.launch {
            try {

                val token = VcontachimApplication.sharedPr.accessToken
                val deleteLike = VcontachimApplication.vcontachimService.deleteLike(
                    itemId = photoId,
                    token = "Bearer $token"
                )
                likeLiveData.value = deleteLike

            } catch (e: Exception) {
                errorLikeLiveData.value = e.message
            }
        }
    }
}