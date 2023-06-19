package com.akhbulatov.vcontachim.model

data class NewsUI(
    val date: Long?,
    val photo200: String?,
    val countComm: Long?,
    val countLike: Long?,
    val postUrl: String?,
    val view: Long?,
    val repostsCount: Long?,
    val name:String?,
    val postID:Int,
    val ownerID:Int,
    val userLikes:Int
)