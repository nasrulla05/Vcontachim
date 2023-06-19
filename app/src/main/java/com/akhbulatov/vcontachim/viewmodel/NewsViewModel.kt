package com.akhbulatov.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.model.News
import com.akhbulatov.vcontachim.model.NewsUI
import kotlinx.coroutines.launch
import kotlin.math.abs

class NewsViewModel : ViewModel() {
    val newsLiveData = MutableLiveData<List<NewsUI>>()
    val errorLiveData = MutableLiveData<String>()
    val progressBarLiveData = MutableLiveData<Boolean>()

    fun loadNews() {
        viewModelScope.launch {
            try {
                val news = VcontachimApplication.vcontachimService.loadNews()

                val newsList = news.response.items.filter {
                    it.attachments?.getOrNull(0)?.type == "photo"
                }

                val newsUI: List<NewsUI> = newsList.map {
                    val groups: List<News.Group> = news.response.groups
                    val profiles: List<News.Profile>? = news.response.profiles
                    val sourceID = abs(it.sourceId)
                    val itemGroup: News.Group? =
                        groups.firstOrNull { group -> group.id == sourceID }

                    var itemProfile: News.Profile? = null
                    if (itemGroup == null) {
                        itemProfile = profiles?.firstOrNull { profile ->
                            profile.id == sourceID
                        }
                    }

                    val ui =
                        NewsUI(
                            photo200 = if (itemGroup != null) itemGroup.photo200 else itemProfile?.photo100,
                            postUrl = it.attachments?.getOrNull(0)?.photo?.sizes?.getOrNull(0)?.url,
                            date = it.date,
                            countComm = it.comments?.count,
                            countLike = it.likes?.count,
                            repostsCount = it.reposts?.count,
                            view = it.views?.count,
                            name = if (itemGroup != null) itemGroup.name else "${itemProfile?.firstName} ${itemProfile?.lastName}"
                        )

                    ui
                }

                progressBarLiveData.value = true

                newsLiveData.value = newsUI

                progressBarLiveData.value = false

            } catch (e: Exception) {
                progressBarLiveData.value = false
                errorLiveData.value = e.message
            }
        }
    }
}