package com.akhbulatov.vcontachim.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.model.UserSearchUi
import kotlinx.coroutines.launch

class UserSearchViewModel : ViewModel() {
    val usersLiveData = MutableLiveData<List<UserSearchUi>>()
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

    @SuppressLint("SuspiciousIndentation")
    fun addFriend(userUi: UserSearchUi) {
        viewModelScope.launch {
            try {
                VcontachimApplication.vcontachimService.addFriend(userId = userUi.id.toLong())

                val list = usersLiveData.value!!.toMutableList()
                val result: UserSearchUi = userUi.copy(
                    isFriend = if (userUi.isFriend == 1) 0 else 1
                )
              val index = list.indexOf(userUi)
                list.set(index,result)

                usersLiveData.value = list
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

                val list = usersLiveData.value!!.toMutableList()
                val result = userUi.copy(
                    isFriend = if (userUi.isFriend == 1) 0 else 1
                )
                val index = list.indexOf(userUi)
                list[index] = result

                usersLiveData.value = list
            } catch (e: Exception) {
                errorLiveData.value = e.message
            } finally {
                progressBarLiveData.value = false
            }
        }
    }
}
