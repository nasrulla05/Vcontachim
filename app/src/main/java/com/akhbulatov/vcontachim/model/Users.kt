package com.akhbulatov.vcontachim.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Users(
    val response: List<Response>
) : Serializable {

    data class Response(
        val id: Long,
        val city: City?,
        @SerializedName("can_send_friend_request")
        val canSendFriendRequest: Int,
        val status: String?,
        @SerializedName("followers_count")
        val followersCount: Long?,
        val career: List<Career>?,
        val counters:Counters,
        @SerializedName("photo_100")
        val photo100: String,
        val online: Long,
        val verified: Long?,
        @SerializedName("is_friend")
        val isFriend:Int,
        @SerializedName("first_name")
        val firstName: String,
        @SerializedName("last_name")
        val lastName: String,
    ) : Serializable

    data class City(
        val title: String?
    ) : Serializable

    data class Career(
        @SerializedName("position")
        val position: String?,
        @SerializedName("company")
        val company: String?,
    ) : Serializable

    data class Counters(
        val followers: Int,
        val friends: Int,
        @SerializedName("online_friends")
        val onlineFriends: Long,
        val pages: Long,
        val photos: Long,
        val subscriptions: Long,
        @SerializedName("user_photos")
        val userPhotos: Long,
        val videos: Long,
        @SerializedName("video_playlists")
        val videoPlaylists: Long,
        @SerializedName("mutual_friends")
        val mutualFriends: Long,
        @SerializedName("clips_followers")
        val clipsFollowers: Long,
    )

}