package com.akhbulatov.vcontachim.model

data class PhotoCommentsUi(
    val id: Long,
    val count: Long,
    val firstName: String,
    val lastName: String,
    val photo: String,
    val textComm: String,
    val date: Int,
    val online: Int,
    val usersLike:Long,
    val ownerId:Long
)