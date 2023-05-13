package com.akhbulatov.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.model.PhotosAlbums
import kotlinx.coroutines.launch

class PhotoAlbumsViewModel : ViewModel() {
    val progressBarLiveData = MutableLiveData<Boolean>()
    val photoAlbumsLiveData = MutableLiveData<PhotosAlbums>()
    val errorLiveData = MutableLiveData<String>()

    fun getPhotoAlbums() {
        viewModelScope.launch {
            try {

                val accessToken = VcontachimApplication.sharedPr.accessToken
                progressBarLiveData.value = true

                val photos: PhotosAlbums =
                    VcontachimApplication.vcontachimService.getAlbums("Bearer $accessToken")
                photoAlbumsLiveData.value = photos

                progressBarLiveData.value = false
            } catch (e: Exception) {
                progressBarLiveData.value = false
                errorLiveData.value = e.message
            }
        }
    }
}