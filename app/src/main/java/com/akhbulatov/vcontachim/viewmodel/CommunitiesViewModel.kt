package com.akhbulatov.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.model.Community
import kotlinx.coroutines.launch

class CommunitiesViewModel : ViewModel() {

    val progressBarLiveData = MutableLiveData<Boolean>()
    val communityLiveData = MutableLiveData<Community>()
    val errorLiveData = MutableLiveData<String>()

    fun getCommunity() {
        viewModelScope.launch {
            try {
                progressBarLiveData.value = true

                val community: Community =
                    VcontachimApplication.vcontachimService.getCommunity()
                communityLiveData.value = community
                progressBarLiveData.value = false

            } catch (e: Exception) {
                progressBarLiveData.value = false
                errorLiveData.value = e.message
            }
        }
    }
}
