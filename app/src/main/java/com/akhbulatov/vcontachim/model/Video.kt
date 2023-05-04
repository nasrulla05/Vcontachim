package com.akhbulatov.vcontachim.model

import com.google.gson.annotations.SerializedName

class Video(
    val response: Response
) {

    class Response(val items: List<Item>)

    class Item(

        @SerializedName("title")
        val title: String,

        @SerializedName("views")
        val views: Int,

        val image: List<Image>,

        @SerializedName("duration")
        val duration: Int
    )

    class Image(
        @SerializedName("url")
        val photo: String
    )

}