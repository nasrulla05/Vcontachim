package com.akhbulatov.vcontachim.model

import com.google.gson.annotations.SerializedName

class PhotosAlbums(
    val response: Response
) {
    class Response(
        val items: List<Items>
    )

    class Items(
        @SerializedName("id")
        val id:Long,

        @SerializedName("size")
        val sizePhoto: Int,

        @SerializedName("title")
        val title: String,

        @SerializedName("thumb_src")
        val avatar: String,
    )

}