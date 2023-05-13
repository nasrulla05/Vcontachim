package com.akhbulatov.vcontachim.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Photos(
    val response: Response
)

data class Response(
    val items: List<Item>
) : Serializable

data class Item(
    val sizes: List<Size>,
    val likes: Likes,
    val comments: Comments,
    val reposts: Reposts,
    val id: Long
) : Serializable

data class Size(
    @SerializedName("url")
    val photo: String
) : Serializable

data class Likes(
    @SerializedName("user_likes")
    val userLikes: Int,

    @SerializedName("count")
    val count: Long
) : Serializable

data class Comments(
    @SerializedName("count")
    val count: Long
) : Serializable

data class Reposts(
    @SerializedName("count")
    val count: Long
) : Serializable


