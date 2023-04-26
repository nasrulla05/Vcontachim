package com.akhbulatov.vcontachim.model

import com.google.gson.annotations.SerializedName

class Community (
    val response:Response
        ){
    class Response(
        val items:List<Item>
    )
    class Item(
        @SerializedName("photo_50")
        val avatar:String,

        @SerializedName("name")
        val name:String,

        @SerializedName("members_count")
        val members:String,

        @SerializedName("verified")
        val verified:String
    )
}
