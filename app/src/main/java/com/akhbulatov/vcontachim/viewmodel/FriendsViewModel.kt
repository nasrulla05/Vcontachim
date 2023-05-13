package com.akhbulatov.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.model.Friends
import kotlinx.coroutines.launch

class FriendsViewModel : ViewModel() {

    val progressBarLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()
    val friendsLiveData = MutableLiveData<Friends>()

    fun main() {
        viewModelScope.launch {
            try {
                val accessToken = VcontachimApplication.sharedPr.accessToken
                progressBarLiveData.value = true

                val friends: Friends =
                    VcontachimApplication.vcontachimService.getFriends("Bearer $accessToken")
                friendsLiveData.value = friends

                progressBarLiveData.value = false

            } catch (e: Exception) {
                progressBarLiveData.value = false
                errorLiveData.value = e.message
            }
        }
    }
}