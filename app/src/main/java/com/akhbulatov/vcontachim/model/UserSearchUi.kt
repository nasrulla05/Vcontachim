package com.akhbulatov.vcontachim.model

data class UserSearchUi(
    val id:Int,
    val description:String?,
    val type:String,
    val photo200:String,
    val online:Int,
    val verified:Int,
    val friendStatus:Int,
    val isFriend:Int,
    val firstName:String,
    val lastName:String
)