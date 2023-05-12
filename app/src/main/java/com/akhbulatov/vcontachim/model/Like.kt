package com.akhbulatov.vcontachim.model

class Like(
   val response: Response
) {
    class Response(
        val likes: Long
    )
}