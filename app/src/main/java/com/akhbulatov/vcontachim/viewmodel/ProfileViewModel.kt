package com.akhbulatov.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhbulatov.vcontachim.model.Root
import com.akhbulatov.vcontachim.network.VcontachimApplication
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    val profileLiveData = MutableLiveData<Root>()
    val failureLiveData = MutableLiveData<String>()

    fun getProfileInfo() {
        viewModelScope.launch {
            try {
                val users = VcontachimApplication.vcontachim.getUsers()
                profileLiveData.value = users

            }catch (e:Exception){
                failureLiveData.value = e.message
            }
        }
    }
}