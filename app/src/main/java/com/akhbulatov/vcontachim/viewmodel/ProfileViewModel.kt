package com.akhbulatov.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.model.Root
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    val profileLiveData = MutableLiveData<Root>()
    val failureLiveData = MutableLiveData<String>()

    fun getProfileInfo() {
        viewModelScope.launch {
            try {

                val accessToken = VcontachimApplication.sharedPr.accessToken
                val users = VcontachimApplication.vcontachimService.getUsers("Bearer $accessToken")
                profileLiveData.value = users

            } catch (e: Exception) {
                failureLiveData.value = e.message
            }
        }
    }
}