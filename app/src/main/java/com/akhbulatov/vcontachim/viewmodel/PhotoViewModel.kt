package com.akhbulatov.vcontachim.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.model.Photos
import kotlinx.coroutines.launch

class PhotoViewModel : ViewModel() {
    val progressBarLiveData = MutableLiveData<Boolean>()
    val photosLiveData = MutableLiveData<Photos>()
    val errorLiveData = MutableLiveData<String>()

    fun main() {
        viewModelScope.launch {
            try {
                val sharedPreferences: SharedPreferences =
                    VcontachimApplication.context.getSharedPreferences(
                        "vcontachim_preferences",
                        Context.MODE_PRIVATE
                    )
                val accessToken = sharedPreferences.getString("access_token", null)

                progressBarLiveData.value = true
                val photos: Photos =
                    VcontachimApplication.vcontachimService.getAlbums("Bearer $accessToken")
                photosLiveData.value = photos
                progressBarLiveData.value = false
            } catch (e: Exception) {
                progressBarLiveData.value = false
                errorLiveData.value = e.message
            }


        }
    }
}