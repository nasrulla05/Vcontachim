package com.akhbulatov.vcontachim.viewmodel

import android.content.Context
import android.content.SharedPreferences
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
                val sharedPreferences: SharedPreferences =
                    VcontachimApplication.context.getSharedPreferences(
                        "vcontachim_preferences",
                        Context.MODE_PRIVATE
                    )
                val authorizedUser = sharedPreferences.getString("access_token",null)
                val users = VcontachimApplication.vcontachim.getUsers("Bearer $authorizedUser")
                profileLiveData.value = users

            } catch (e: Exception) {
                failureLiveData.value = e.message
            }
        }
    }
}