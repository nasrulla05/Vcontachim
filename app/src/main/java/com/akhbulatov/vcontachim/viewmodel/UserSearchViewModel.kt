package com.akhbulatov.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.model.HistoryUser
import com.akhbulatov.vcontachim.model.UserSearchUi
import com.akhbulatov.vcontachim.utility.SharedPreferencesManager
import kotlinx.coroutines.launch

class UserSearchViewModel : ViewModel() {
    val usersLiveData = MutableLiveData<List<UserSearchUi>>()
    val historyLiveData = MutableLiveData<List<HistoryUser>>()
    val progressBarLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()

    fun searchUser(requestText: String) {
        viewModelScope.launch {
            try {
                progressBarLiveData.value = true

                val usSearch =
                    VcontachimApplication.vcontachimService.searchUsers(requestText = requestText)
                val filterList = usSearch.response.items.filter { item -> item.type == "profile" }

                val userList = filterList.map {

                    val ui =
                        UserSearchUi(
                            id = it.profile?.id!!,
                            description = it.description,
                            type = it.type,
                            photo200 = it.profile.photo200,
                            isFriend = it.profile.friend,
                            online = it.profile.online,
                            verified = it.profile.verified,
                            friendStatus = it.profile.friendStatus,
                            firstName = it.profile.firstName,
                            lastName = it.profile.lastName
                        )
                    ui
                }
                usersLiveData.value = userList

            } catch (e: Exception) {
                errorLiveData.value = e.message

            } finally {
                progressBarLiveData.value = false
            }
        }
    }

    fun clearList() {
        val list = emptyList<UserSearchUi>()
        usersLiveData.value = list
    }

    fun addFriend(userUi: UserSearchUi) {
        viewModelScope.launch {
            try {
                VcontachimApplication.vcontachimService.addFriend(userId = userUi.id.toLong())

                val mutableList = usersLiveData.value!!.toMutableList()
                val result: UserSearchUi = userUi.copy(
                    isFriend = if (userUi.isFriend == 1) 0 else 1
                )
                val index = mutableList.indexOf(userUi)
                mutableList.set(index, result)

                usersLiveData.value = mutableList
            } catch (e: Exception) {
                errorLiveData.value = e.message
            } finally {
                progressBarLiveData.value = false
            }
        }
    }

    fun deleteFriend(userUi: UserSearchUi) {
        viewModelScope.launch {
            try {
                VcontachimApplication.vcontachimService.deleteFriend(userUi.id.toLong())

                val mutableList = usersLiveData.value!!.toMutableList()
                val result = userUi.copy(
                    isFriend = if (userUi.isFriend == 1) 0 else 1
                )
                val index = mutableList.indexOf(userUi)
                mutableList[index] = result

                usersLiveData.value = mutableList
            } catch (e: Exception) {
                errorLiveData.value = e.message
            } finally {
                progressBarLiveData.value = false
            }
        }
    }

    fun addElement(element: HistoryUser) {
        val list = historyLiveData.value!!.toMutableList()
        list.add(element)
        historyLiveData.value = list
    }

    fun deleteElement(element: HistoryUser) {
        val list = historyLiveData.value!!.toMutableList()
        list.remove(element)
        historyLiveData.value = list
    }

    fun clearListHistory() {
        val list = emptyList<HistoryUser>()
        historyLiveData.value = list
    }

    fun maxLengthHistory() {
        val list = historyLiveData.value!!
        val mutList = list.toMutableList()
        if (list.size > 6) {
            mutList.removeAt(5)
        }
        SharedPreferencesManager.saveHistory(mutList)
    }
}
