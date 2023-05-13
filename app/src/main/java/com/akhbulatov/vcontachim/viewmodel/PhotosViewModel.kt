package com.akhbulatov.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.model.Photos
import kotlinx.coroutines.launch

class PhotosViewModel : ViewModel() {
    val progressBarLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()
    val photosLiveData = MutableLiveData<Photos>()

    fun getPhotos(id: Long) {
        viewModelScope.launch {
            try {
                val accessToken: String? = VcontachimApplication.sharedPr.accessToken
                progressBarLiveData.value = true

                val photos =
                    VcontachimApplication.vcontachimService.getPhotos(
                        "Bearer $accessToken",
                        albumId = id
                    )
                photosLiveData.value = photos

                progressBarLiveData.value = false

            } catch (e: Exception) {

                progressBarLiveData.value = false
                errorLiveData.value = e.message

            }
        }
    }
}