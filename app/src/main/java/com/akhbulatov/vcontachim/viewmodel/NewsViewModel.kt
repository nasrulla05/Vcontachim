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

    fun loadNews() {
        viewModelScope.launch {
            try {
                val news = VcontachimApplication.vcontachimService.loadNews()
                val itemPhoto = news.response.items[0].copyHistory?.get(0)

                val newsList = news.response.items.filter {
                    itemPhoto?.type == "photo"
                }
                val newsUI: List<NewsUI> = newsList.map {
                    val groups: List<News.Group> = news.response.groups
                    val sourceID = abs(it.sourceId)
                    val itemGroup =
                        groups.first { group -> if (group.id == sourceID) true else false }

                    val ui =
                        NewsUI(
                            photo200 = itemGroup.photo200,
                            postUrl = it.copyHistory?.get(0)?.attachments?.get(0)?.photo?.sizes?.get(
                                0
                            )?.url,
                            date = it.copyHistory?.get(0)?.date,
                            countComm = it.comments.count,
                            countLike = it.likes.count,
                            repostsCount = it.reposts.count,
                            view = it.views.count,
//                            firstName = itemProfile.firstName,
//                            lastName = itemProfile.lastName,
//                            photo100 = itemProfile.photo100,
//                            id = itemProfile.id,
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