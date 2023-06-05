package com.akhbulatov.vcontachim.viewmodel

import Users
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.model.Root
import kotlinx.coroutines.launch

class InfoProfileViewModel : ViewModel() {

    val infoProfileLiveData = MutableLiveData<Users>()
    val errorLiveData = MutableLiveData<String>()

    fun loadInfoProfile(user: Root.User) {
        viewModelScope.launch {
            try {
                infoProfileLiveData.value =
                    VcontachimApplication.vcontachimService.getInfoProfile(userIds = user.id)

            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }
}