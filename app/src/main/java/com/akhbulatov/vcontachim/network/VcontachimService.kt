package com.akhbulatov.vcontachim.network

import com.akhbulatov.vcontachim.model.Root
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface VcontachimService {
    @GET("users.get")
    suspend fun getUsers(
        @Header("Authorization") token: String,
        @Query("v") v: Double = 5.131,
        @Query("fields") fields: String = "contacts,photo_100"
    ): Root
}