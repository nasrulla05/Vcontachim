package com.akhbulatov.vcontachim.model

import com.google.gson.annotations.SerializedName

class PhotoComments(
    val response: ResponsePhotoComments,
) {
    data class ResponsePhotoComments(
        val count: Long,
        val items: List<ItemPhotoComments>,
        val profiles: List<Profile>
    )

    data class ItemPhotoComments(
        @SerializedName("from_id")
        val fromId: Long,

        val id: Long,

        @SerializedName("date")
        val date: Int,

        @SerializedName("text")
        val textComments: String,

        val likes:Likes
    )

    data class Likes(
        @SerializedName("user_likes")
        val userLikes:Long
        )

    data class Profile(
        @SerializedName("id")
        val id: Long,
        @SerializedName("photo_200")
        val photo: String,
        @SerializedName("online")
        val online: Int,
        @SerializedName("first_name")
        val firstName: String,
        @SerializedName("last_name")
        val lastName: String
    )
}