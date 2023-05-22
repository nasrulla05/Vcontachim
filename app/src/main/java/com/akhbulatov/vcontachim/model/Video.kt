package com.akhbulatov.vcontachim.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Video(
    val response: Response
) : Serializable {

    class Response(val items: List<Item>)

  data class Item(
        val likes: Likes,

        val reposts: Reposts,

        val image: List<Image>,

        @SerializedName("owner_id")
        val ownerId: Int,

        @SerializedName("id")
        val itemId: Int,

        @SerializedName("player")
        val player: String,

        @SerializedName("title")
        val title: String,

        @SerializedName("views")
        val views: Int,

        @SerializedName("duration")
        val duration: Int,

        @SerializedName("date")
        val date: Long,

        @SerializedName("comments")
        val comments: Int

        ) : Serializable

    class Image(
        @SerializedName("url")
        val photo: String
    ) : Serializable

 data class Likes(
        @SerializedName("user_likes")
        val userLikes: Int,

        @SerializedName("count")
        val likesCount: Int
    ) : Serializable

    class Reposts(
        @SerializedName("count")
        val reposts_count: Int
    ) : Serializable

}