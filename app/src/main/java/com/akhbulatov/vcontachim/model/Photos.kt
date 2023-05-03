package com.akhbulatov.vcontachim.model

import com.google.gson.annotations.SerializedName

class Photos(
    val response: Response
) {
    class Response(
        val items:List<Item>
    )

    class Item(
        val sizes: List<Size>
    )

    class Size(
        @SerializedName("url")
        val photo: String
    )
}

