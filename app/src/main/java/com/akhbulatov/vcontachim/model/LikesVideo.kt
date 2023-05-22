package com.akhbulatov.vcontachim.model

class LikesVideo(
    val response: ResponseVideo
) {
    class ResponseVideo(
        val likes: Int
    )
}