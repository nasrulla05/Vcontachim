package com.akhbulatov.vcontachim.model

import java.io.Serializable

data class VideoCommentsUI(
    val id: Long,
    val firstName: String,
    val surName: String,
    val count: Long,
    val userLikes: Long,
    val fromId: Long,
    val date: Long,
    val text: String,
): Serializable