package com.akhbulatov.vcontachim.viewmodel

import android.content.Context
import android.content.SharedPreferences
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
                val sharedPreferences: SharedPreferences =
                    VcontachimApplication.context.getSharedPreferences(
                        "vcontachim_preferences",
                        Context.MODE_PRIVATE
                    )
                val accessToken = sharedPreferences.getString("access_token", null)

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