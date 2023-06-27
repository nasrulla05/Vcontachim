package com.akhbulatov.vcontachim.model

import com.google.gson.annotations.SerializedName

data class UsersSearch(
    val response: Response
) {

    data class Response(
        val count: Long,
        val items: List<Item>,
    )

    data class Item(
        val description: String,
        val type: String,
        val profile: Profile?,
        val group: Group?,
    )

    data class Profile(
        val id: Long,
        @SerializedName("photo_200")
        val photo200: String,
        @SerializedName("is_friend")
        val friend: Int,
        @SerializedName("online")
        val online:Int,
        @SerializedName("verified")
        val verified: Int,
        @SerializedName("friend_status")
        val friendStatus:Int,
        @SerializedName("first_name")
        val firstName: String,
        @SerializedName("last_name")
        val lastName: String,
        @SerializedName("can_access_closed")
        val canAccessClosed: Boolean,
        @SerializedName("is_closed")
        val isClosed: Boolean,
        @SerializedName("track_code")
        val trackCode: String,
        val city: City?,
    )

    data class City(
        val id: Long,
        val title: String,
    )

    data class Group(
        val id: Long,
        val like: Like,
        val name: String,
        @SerializedName("screen_name")
        val screenName: String
    )

}