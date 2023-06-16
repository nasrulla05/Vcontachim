package com.akhbulatov.vcontachim.model

import com.google.gson.annotations.SerializedName

data class News(
    val response: Response,
) {

    data class Response(
        val items: List<Item>,
        val groups: List<Group>,
        val profile: List<Profile>
    )

    data class Item(
        val type: String,
        @SerializedName("source_id")
        val sourceId: Long,
        val date: Long,
        @SerializedName("copy_history")
        val copyHistory: List<CopyHistory>?,
        val comments: Comments,
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
        val views: Views
    )

    data class Profile(
        val id: Long,
        @SerializedName("photo_100")
        val photo100: String?,
        @SerializedName("first_name")
        val firstName: String?,
        @SerializedName("last_name")
        val lastName: String?
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
        val type:String,
        val photo: Photo?,
        val video:Video?
    )

    data class Photo(
        @SerializedName("owner_id")
        val ownerId: Long,
        val id: Long,
        val postId: Long,
        val sizes: List<Size>,
        val text: String?
    )

    data class Video(
        @SerializedName("first_frame")
        val firstFrame: List<FirstFrame>?,
        val width: Long,
        val height: Long,
        val id: Long,
        @SerializedName("owner_id")
        val ownerId: Long,
        val title: String
    )
    data class Size(
        val url: String?
    )

    data class FirstFrame(
        val url: String?
    )

    data class Comments(
        val count: Long?
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
        @SerializedName("photo_200")
        val photo200: String?,
    )

    data class Views(
        val count: Long
    )
}
