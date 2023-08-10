package com.akhbulatov.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.database.HistoryDao
import com.akhbulatov.vcontachim.database.HistoryUser
import com.akhbulatov.vcontachim.model.UserSearchUi
import kotlinx.coroutines.launch

class UserSearchViewModel : ViewModel() {
    val usersLiveData = MutableLiveData<List<UserSearchUi>>()
    val historyLiveData = MutableLiveData<List<HistoryUser>>()
    val progressBarLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()

    private val historyDao: HistoryDao = VcontachimApplication.historyDatabase.historyDao()

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

    fun clearUsers() {
        val list = emptyList<UserSearchUi>()
        usersLiveData.value = list
    }

    fun loadHistory() {
        viewModelScope.launch {
            val list: List<HistoryUser> = historyDao.getAllHistory()
            val list2 = list.take(6)
            historyLiveData.value = list2
        }
    }

    fun addElement(element: HistoryUser) {
        viewModelScope.launch {
            historyDao.addHistory(element)
            val list: List<HistoryUser> = historyDao.getAllHistory()
            historyLiveData.value = list
        }
    }

    fun deleteElement(element: HistoryUser) {
        viewModelScope.launch {
            historyDao.deleteHistoryItem(element)
            val list: List<HistoryUser> = historyDao.getAllHistory()
            historyLiveData.value = list
        }
    }

    fun clearListHistory() {
        viewModelScope.launch {
            VcontachimApplication.historyDatabase.historyDao().deleteHistoryList()
        }
    }
}
