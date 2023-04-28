package com.akhbulatov.vcontachim.model

import com.google.gson.annotations.SerializedName

class Photos(
    val response: Response
) {
    class Response(
        items: List<Items>
    )

    class Items(
        @SerializedName("size")
        val sizePhoto: Int?,

        @SerializedName("title")
        val title: String,

        @SerializedName("thumb_src")
        val avatar: String
    )
}