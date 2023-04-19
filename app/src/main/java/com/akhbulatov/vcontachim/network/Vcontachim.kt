package com.akhbulatov.vcontachim.network

import com.akhbulatov.vcontachim.model.Root
import retrofit2.http.GET
import retrofit2.http.Query

interface Vcontachim {
    @GET("user.get")
    suspend fun getUsers(
        @Query("fields") fields: String = "photo_100",
        @Query("v") v: Double = 5.131
    ): Root
}