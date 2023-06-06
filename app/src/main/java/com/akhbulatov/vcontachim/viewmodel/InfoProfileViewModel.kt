package com.akhbulatov.vcontachim.viewmodel

import Users
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhbulatov.vcontachim.VcontachimApplication
import kotlinx.coroutines.launch

class InfoProfileViewModel : ViewModel() {

    val infoProfileLiveData = MutableLiveData<Users.Response>()
    val errorLiveData = MutableLiveData<String>()

    fun loadInfoProfile(id:Long) {
        viewModelScope.launch {
            try {
                infoProfileLiveData.value =
                    VcontachimApplication.vcontachimService.getInfoProfile(userIds = id)

            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }
}