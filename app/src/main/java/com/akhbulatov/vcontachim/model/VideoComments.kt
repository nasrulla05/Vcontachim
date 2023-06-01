package com.akhbulatov.vcontachim.model

import com.google.gson.annotations.SerializedName

class VideoComments(
    val response: Response
) {
    class Response(
        val items: List<Item>,
        val profiles: List<Profile>,
    )

    class Item(
        val id: Long,
        @SerializedName("from_id")
        val fromId: Long,
        val date: Long,
        val text: String,
        val likes: Likes
    )

    data class Likes(
        val count: Long,
        val userLikes: Long
    )

    data class Profile(
        val id: Long,
        @SerializedName("first_name")
        val firsName: String,
        @SerializedName("last_name")
        val lastName: String
    )
}