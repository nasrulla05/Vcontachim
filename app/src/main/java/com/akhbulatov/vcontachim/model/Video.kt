package com.akhbulatov.vcontachim.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Video(
    val response: Response
): Serializable {

    class Response(val items: List<Item>)

    class Item(
        @SerializedName("id")
        val id: Int,

        @SerializedName("player")
        val player: String,

        @SerializedName("title")
        val title: String,

        @SerializedName("views")
        val views: Int,

        val image: List<Image>,

        @SerializedName("duration")
        val duration: Int,

        @SerializedName("date")
        val date: Long,

        @SerializedName("comments")
        val comments: Int,

        val likes: Likes,

        val reposts: Reposts,

        ): Serializable

    class Image(
        @SerializedName("url")
        val photo: String
    ): Serializable

    class Likes(
        @SerializedName("count")
        val likes_count: Int
    ): Serializable

    class Reposts(
        @SerializedName("count")
        val reposts_count: Int
    ): Serializable

}