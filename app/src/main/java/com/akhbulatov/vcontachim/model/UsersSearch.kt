package com.akhbulatov.vcontachim.model

import com.google.gson.annotations.SerializedName

data class UsersSearch(
    val response: Response
) {

    data class Response(
        val items: List<Item>
    )

    data class Item(
        val description: String,
        val type: String,
        val profile: Profile?
    )

    data class Profile(
        val id: Int,
        @SerializedName("photo_200")
        val photo200: String,
        @SerializedName("is_friend")
        val friend: Int,
        @SerializedName("online")
        val online: Int,
        @SerializedName("verified")
        val verified: Int,
        @SerializedName("friend_status")
        val friendStatus: Int,
        @SerializedName("first_name")
        val firstName: String,
        @SerializedName("last_name")
        val lastName: String,
    )
}