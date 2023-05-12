package com.akhbulatov.vcontachim.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Photos(
    val response: Response
)

class Response(
    val items: List<Item>
) : Serializable

class Item(
    val sizes: List<Size>,
    val likes: Likes,
    val comments: Comments,
    val reposts: Reposts,
    val id: Long
) : Serializable

class Size(
    @SerializedName("url")
    val photo: String
) : Serializable

class Likes(
    @SerializedName("user_likes")
    val userLikes: Int,

    @SerializedName("count")
    val count: Long
) : Serializable

class Comments(
    @SerializedName("count")
    val count: Long
) : Serializable

class Reposts(
    @SerializedName("count")
    val count: Long
) : Serializable


