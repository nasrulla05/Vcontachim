package com.akhbulatov.vcontachim.network

import com.akhbulatov.vcontachim.model.*
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface VcontachimService {

    @GET("users.get")
    suspend fun getUsers(
        @Query("v") v: Double = 5.131,
        @Query("fields") fields: String = "contacts,photo_100"
    ): Root

    @GET("friends.get")
    suspend fun getFriends(
        @Query("v") v: Double = 5.131,
        @Query("fields") fields: String = "photo_100"
    ): Friends

    @GET("groups.get")
    suspend fun getCommunity(
        @Query("v") v: Double = 5.131,
        @Query("extended") extended: Int = 1,
        @Query("fields") fields: String = "members_count,verified"
    ): Community

    @GET("photos.getAlbums")
    suspend fun getAlbums(
        @Query("v") v: Double = 5.131,
        @Query("need_covers") needCovers: Int = 1
    ): PhotosAlbums

    @GET("photos.get")
    suspend fun getPhotos(
        @Query("v") v: Double = 5.131,
        @Query("album_id") albumId: Long,
        @Query("extended") extended: Int = 1
    ): Photos

    @GET("video.get")
    suspend fun getVideo(
        @Query("v") v: Double = 5.131,
        @Query("extended") extended: Int = 1,
    ): Video

    @POST("likes.add")
    suspend fun postLike(
        @Query("v") v: Double = 5.131,
        @Query("type") type: String = "photo",
        @Query("item_id") itemId: Long
    ): Like

    @POST("likes.delete")
    suspend fun deleteLike(
        @Query("v") v: Double = 5.131,
        @Query("type") type: String = "photo",
        @Query("item_id") itemId: Long
    ): Like

    @POST("video.delete")
    suspend fun deleteVideo(
        @Query("v") v: Double = 5.131,
        @Query("video_id") videoId: Int
    )

    @POST("likes.add")
    suspend fun postVideo(
        @Query("v") v: Double = 5.131,
        @Query("type") type: String = "video",
        @Query("item_id") itemId: Int,
        @Query("owner_id") ownerId: Int
    ): LikesVideo

    @GET("photos.getComments")
    suspend fun getComments(
        @Query("v") v: Double = 5.131,
        @Query("photo_id") photoId: Long,
        @Query("need_likes") needLikes: Int = 1,
        @Query("fields") fields: String = "photo_200,online",
        @Query("extended") extended: Int = 1
    ): PhotoComments
}