package com.akhbulatov.vcontachim.network

import com.akhbulatov.vcontachim.model.*
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface VcontachimService {

    @GET("users.get")
    suspend fun getUsers(
        @Header("Authorization") token: String,
        @Query("v") v: Double = 5.131,
        @Query("fields") fields: String = "contacts,photo_100"
    ): Root

    @GET("friends.get")
    suspend fun getFriends(
        @Header("Authorization") token: String,
        @Query("v") v: Double = 5.131,
        @Query("fields") fields: String = "photo_100"
    ): Friends

    @GET("groups.get")
    suspend fun getCommunity(
        @Header("Authorization") token: String,
        @Query("v") v: Double = 5.131,
        @Query("extended") extended: Int = 1,
        @Query("fields") fields: String = "members_count,verified"
    ): Community

    @GET("photos.getAlbums")
    suspend fun getAlbums(
        @Header("Authorization") token: String,
        @Query("v") v: Double = 5.131,
        @Query("need_covers") needCovers: Int = 1
    ): PhotosAlbums

    @GET("photos.get")
    suspend fun getPhotos(
        @Header("Authorization") token: String,
        @Query("v") v: Double = 5.131,
        @Query("album_id") albumId: Long,
        @Query("extended") extended: Int = 1
    ): Photos

    @GET("video.get")
    suspend fun getVideo(
        @Header("Authorization") token: String,
        @Query("v") v: Double = 5.131,
        @Query("extended") extended: Int = 1
    ): Video

    @POST("likes.add")
    suspend fun getLike(
        @Header("Authorization") token: String,
        @Query("v") v: Double = 5.131,
        @Query("type") type: String = "photo",
        @Query("item_id") itemId: Long
    ): Likes
}