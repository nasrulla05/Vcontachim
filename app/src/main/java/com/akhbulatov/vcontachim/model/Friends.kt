package com.akhbulatov.vcontachim.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Friends(
    val response: Response
) : Serializable {
    class Response(
        val items: List<Item>
    )

    class Item(
        @SerializedName("id")
        val id: Long,

        @SerializedName("first_name")
        val firstName: String,

        @SerializedName("last_name")
        val lastName: String,

        @SerializedName("photo_100")
        val avatar100: String
    ) : Serializable
}