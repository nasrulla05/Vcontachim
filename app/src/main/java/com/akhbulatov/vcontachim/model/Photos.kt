package com.akhbulatov.vcontachim.model

import com.google.gson.annotations.SerializedName

class Photos(
    val response: Response
) {
    class Response(
        val items: List<Item>
    )

    class Item(
        val sizes: List<Size>,
        val likes: Likes,
        val comments: Comments,
        val reposts: Reposts,
    )

    class Size(
        @SerializedName("url")
        val photo: String
    )

    class Likes(
        @SerializedName("count")
        val count: Long
    )

    class Comments(
        @SerializedName("count")
        val count: Long
    )

    class Reposts(
        @SerializedName("count")
        val count: Long
    )
}

