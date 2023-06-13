package com.akhbulatov.vcontachim.model

import java.io.Serializable

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
): Serializable