package com.akhbulatov.vcontachim.model

import com.google.gson.annotations.SerializedName


class Friends(
    val response: Response
) {
    class Response(
        val items: List<Item>
    )

    class Item(
        @SerializedName("first_name")
        val firstName: String,

        @SerializedName("last_name")
        val lastName: String,

        @SerializedName("photo_100")
        val avatar100: String
    )
}