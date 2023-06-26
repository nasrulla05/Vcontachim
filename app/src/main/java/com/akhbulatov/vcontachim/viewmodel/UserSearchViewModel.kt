package com.akhbulatov.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.model.UsersSearch
import kotlinx.coroutines.launch

class UserSearchViewModel : ViewModel() {
    val usersLiveData = MutableLiveData<List<UsersSearch.Item>>()
    val progressBarLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()

    fun searchUser(requestText: String) {
        viewModelScope.launch {
            try {
                val usSearch =
                    VcontachimApplication.vcontachimService.searchUsers(requestText = requestText)
                val profile = usSearch.response.items.filter { item -> item.type == "profile" }

                progressBarLiveData.value = true
                usersLiveData.value = profile

                progressBarLiveData.value = false

            } catch (e: Exception) {
                progressBarLiveData.value = false
                errorLiveData.value = e.message
            }
        }
    }
}
