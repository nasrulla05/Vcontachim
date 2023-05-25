package com.akhbulatov.vcontachim.model

data class LikeComment(
    val response: Like
):java.io.Serializable{
    data class Like(
        val likes:Long
    ):java.io.Serializable
}
