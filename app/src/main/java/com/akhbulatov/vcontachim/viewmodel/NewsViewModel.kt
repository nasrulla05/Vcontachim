package com.akhbulatov.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.model.News
import com.akhbulatov.vcontachim.model.NewsUI
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    val newsLiveData = MutableLiveData<List<NewsUI>>()
    val errorLiveData = MutableLiveData<String>()

    fun loadNews() {
        viewModelScope.launch {
            try {
                val news = VcontachimApplication.vcontachimService.loadNews()
                val newsUI = news.response.items.map {
                    val groups: List<News.Group> = news.response.groups

                    val item = groups.first { groups ->
                        if (groups.id == it.id) true else false
                    }

                    val url = it.copyHistory[0].attachments[0].photo?.sizes?.get(0)?.url

                    val ui = NewsUI(
                        photo200 = item.photo200,
                        postUrl = url,
                        text = it.text,
                        date = it.date,
                        countComm = it.comments.count,
                        countLike = it.likes.count,
                        repostsCount = it.reposts.count,
                        view = it.views.count
                    )
                    ui
                }

                newsLiveData.value = newsUI

            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }
}