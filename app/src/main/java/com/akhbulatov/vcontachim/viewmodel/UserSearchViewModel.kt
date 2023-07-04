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
                progressBarLiveData.value = true

                val usSearch =
                        VcontachimApplication.vcontachimService.searchUsers(requestText = requestText)
                val profile = usSearch.response.items.filter { item -> item.type == "profile" }

                usersLiveData.value = profile

            } catch (e: Exception) {
                errorLiveData.value = e.message

            } finally {
                progressBarLiveData.value = false
            }
        }
    }

    fun clearList() {
        val list = emptyList<UsersSearch.Item>()
        usersLiveData.value = list
    }

    fun addFriend(id: Int) {
        viewModelScope.launch {
            try {
                VcontachimApplication.vcontachimService.addSearchFriend(userId = id)
            } catch (e: Exception) {
                errorLiveData.value = e.message
            } finally {
                progressBarLiveData.value = false
            }
        }
    }

    fun deleteFriend(id: UsersSearch.Item) {
        viewModelScope.launch {
            try {
                VcontachimApplication.vcontachimService.deleteSearchFriend(id.profile?.id!!)

            } catch (e: Exception) {
                errorLiveData.value = e.message
            } finally {
                progressBarLiveData.value = false
            }
        }
    }
}
