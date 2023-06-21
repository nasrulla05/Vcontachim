package com.akhbulatov.vcontachim.model

data class NewsUi(
    val photo: String?,
    val postUrl: String?,
    val date: Long?,
    val countComm: Long?,
    val countLike: Long?,
    val repostsCount: Long?,
    val view: Long?,
    val name: String,
    val postId: Int,
    val ownerId: Int,
    val userLikes: Int,
    val photoList:List<News.Attachment>?
)