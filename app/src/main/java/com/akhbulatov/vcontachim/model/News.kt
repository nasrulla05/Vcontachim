package com.akhbulatov.vcontachim.model

import com.google.gson.annotations.SerializedName

data class News(
    val response: Response,
) {

    data class Response(
        val items: List<Item>,
        val groups: List<Group>
    )

    data class Item(
        val type: String,
        @SerializedName("source_id")
        val sourceId: Long,
        val date: Long,
        @SerializedName("copy_history")
        val copyHistory: List<CopyHistory>,
        val comments: Comments,
        val attachments: List<Any?>,
        val id: Long,
        val likes: Likes,
        @SerializedName("owner_id")
        val ownerId: Long,
        @SerializedName("post_id")
        val postId: Long,
        @SerializedName("post_type")
        val postType: String,
        val reposts: Reposts,
        val text: String,
        val views:Views
    )

    data class CopyHistory(
        val type: String,
        val attachments: List<Attachment>,
        val date: Long,
        @SerializedName("post_type")
        val postType: String,
        val text: String,
    )

    data class Attachment(
        val type: String,
        val photo: Photo?
    )

    data class Photo(
        val id: Long,
        val postId: Long,
        val sizes: List<Size>,
        val text: String
    )

    data class Size(
        val height: Long,
        val type: String,
        val width: Long,
        val url: String,
    )

    data class Comments(
        val count: Long
    )

    data class Likes(
        val count: Long,
        @SerializedName("user_likes")
        val userLikes: Long
    )

    data class Reposts(
        val count: Long,
        @SerializedName("user_reposted")
        val userReposted: Long,
    )

    data class Group(
        val id: Long,
        val name: String,
        val type: String,
        @SerializedName("photo_200")
        val photo200: String?,
    )

    data class Views(
        val count: Long,
    )
}
