package com.akhbulatov.vcontachim.model

import java.io.Serializable

data class LikeComment(
    val response: Like
): Serializable {
    data class Like(
        val likes:Long
    ):Serializable
}
