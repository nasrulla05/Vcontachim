package com.akhbulatov.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akhbulatov.vcontachim.model.HistoryUser
import com.akhbulatov.vcontachim.utility.SharedPreferencesManager

class HistoryViewModel : ViewModel() {
     val historyLiveData = MutableLiveData<List<HistoryUser>>()

    fun addElement(element: HistoryUser) {
        val list = mutableListOf<HistoryUser>()
        list.add(element)
        historyLiveData.value = list
    }

    fun deleteElement(element: HistoryUser) {
        val list = mutableListOf<HistoryUser>()
        list.remove(element)
        historyLiveData.value = list
    }

    fun clearList(){
        val list = emptyList<HistoryUser>()
        historyLiveData.value = list
    }

    fun removeElement(){
        val list = historyLiveData.value!!
        val mutList = list.toMutableList()
         if (list.size > 6){
             mutList.removeAt(0)
         }

        SharedPreferencesManager
    }
}