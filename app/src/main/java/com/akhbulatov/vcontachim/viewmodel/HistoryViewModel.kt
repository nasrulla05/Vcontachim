package com.akhbulatov.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akhbulatov.vcontachim.model.HistoryUser

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
}