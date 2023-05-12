package com.akhbulatov.vcontachim.viewmodel

import android.content.Context
import android.content.SharedPreferences
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
                val sharedPreferences: SharedPreferences =
                    VcontachimApplication.context.getSharedPreferences(
                        "vcontachim_preferences",
                        Context.MODE_PRIVATE
                    )

                val token = sharedPreferences.getString("access_token", null)
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
                val sharedPreferences: SharedPreferences =
                    VcontachimApplication.context.getSharedPreferences(
                        "vcontachim_preferences",
                        Context.MODE_PRIVATE
                    )

                val token = sharedPreferences.getString("access_token", null)

                val deleteLike = VcontachimApplication.vcontachimService.postDelete(
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