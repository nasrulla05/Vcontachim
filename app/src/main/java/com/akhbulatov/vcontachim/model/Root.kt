package com.akhbulatov.vcontachim.model

import com.google.gson.annotations.SerializedName

class Root(
    val response: List<User>
)
class User(
    @SerializedName("photo_100")
    val avatar: String,

    @SerializedName("first_name")
    val name: String,

    @SerializedName("last_name")
    val surname: String,

    @SerializedName("mobile_phone")
    val mobile_phone: String?

) : java.io.Serializable
