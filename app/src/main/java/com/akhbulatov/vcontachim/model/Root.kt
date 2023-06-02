package com.akhbulatov.vcontachim.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Root(
    val response: List<User>
) : Serializable {
    class User(
        val id: Long,

        val city: City,

        @SerializedName("status")
        val status:String,

        @SerializedName("followers_count")
        val followersCount: Long,

        val career: List<Career>,

        val online: Long,

        @SerializedName("verified")
        val verified:Long,

        @SerializedName("friend_status")
        val friendStatus: Long,

        @SerializedName("photo_100")
        val avatar: String,

        @SerializedName("first_name")
        val name: String,

        @SerializedName("last_name")
        val surname: String,

        @SerializedName("mobile_phone")
        val mobile_phone: String?

    ) : Serializable

    data class City(
        val title: String
    ) : Serializable

    data class Career(
        @SerializedName("group_id")
        val groupId:Long
    ) : Serializable
}
