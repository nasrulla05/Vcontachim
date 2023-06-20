package com.akhbulatov.vcontachim.model

data class NewsUi(
    val date: Long?,
    val photo200: String?,
    val countComm: Long?,
    val countLike: Long?,
    val postUrl: String?,
    val view: Long?,
    val repostsCount: Long?,
    val name: String?,
    val postId: Int,
    val ownerId: Int,
    val userLikes: Int
)