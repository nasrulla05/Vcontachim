package com.akhbulatov.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.model.Users
import kotlinx.coroutines.launch

class InfoProfileViewModel : ViewModel() {
    val infoProfileLiveData = MutableLiveData<Users.Response>()
    val errorLiveData = MutableLiveData<String>()

    fun loadInfoProfile(id: Long) {
        viewModelScope.launch {
            try {
                infoProfileLiveData.value =
                    VcontachimApplication.vcontachimService.getInfoProfile(userIds = id).response[0]

            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }

    fun addFriend(id: Long) {
        viewModelScope.launch {
            try {
                VcontachimApplication.vcontachimService.addFriend(id)

                val infoProf = infoProfileLiveData.value!!
                val modifiedInfoProfile =
                    // is Friend yes == 1 no == 0
                    infoProf.copy(isFriend = if (infoProf.isFriend == 1) 0 else 1)

                infoProfileLiveData.value = modifiedInfoProfile
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }

        }
    }

    fun deleteFriend(id: Long) {
        viewModelScope.launch {
            try {
                VcontachimApplication.vcontachimService.deleteFriend(id)

                val infoProf = infoProfileLiveData.value!!
                val modifiedInfoProfile =
                    infoProf.copy(isFriend = if (infoProf.isFriend == 1) 0 else 1)

                infoProfileLiveData.value = modifiedInfoProfile

            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }
}