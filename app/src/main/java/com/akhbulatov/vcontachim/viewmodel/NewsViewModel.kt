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
                            countLike = it.likes.count,
                            repostsCount = it.reposts?.count,
                            view = it.views?.count,
                            name = if (itemGroup != null) itemGroup.name else "${itemProfile?.firstName} ${itemProfile?.lastName}",
                            postID = it.postId,
                            ownerID = it.ownerId,
                            userLikes = it.likes.userLikes
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

    fun addLike(newsUI: NewsUI) {
        viewModelScope.launch {
            try {
                VcontachimApplication.vcontachimService.addLikePost(
                    postID = newsUI.postID,
                    ownerID = newsUI.ownerID
                )

                val mutList = newsLiveData.value?.toMutableList()
                val result = newsUI.copy(
                    userLikes = if (newsUI.userLikes == 1) 0 else 1,
                    countLike = newsUI.countLike?.plus(1)
                )
                val index = mutList!!.indexOf(newsUI)
                mutList[index] = result

                newsLiveData.value = mutList
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }

    fun deleteLike(newsUI: NewsUI) {
        viewModelScope.launch {
            try {
                VcontachimApplication.vcontachimService.deleteLikePost(
                    postId = newsUI.postID,
                    ownerId = newsUI.ownerID
                )

                val mutList = newsLiveData.value?.toMutableList()
                val result = newsUI.copy(
                    userLikes = if (newsUI.userLikes == 1) 0 else 1,
                    countLike = newsUI.countLike?.minus(1)
                )
                val index = mutList!!.indexOf(newsUI)
                mutList[index] = result

                newsLiveData.value = mutList
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }
}