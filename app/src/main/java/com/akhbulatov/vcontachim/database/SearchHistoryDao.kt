package com.akhbulatov.vcontachim.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface SearchHistoryDao {

    @Query("select*from searchhistory")
    suspend fun getAllHistory(): SearchHistory

    @Query("delete from searchhistory")
    suspend fun getDeleteHistory(): List<SearchHistory>

    @Insert
    suspend fun addHistotry(history: SearchHistory)

    @Delete
    suspend fun deleteHisory(history: SearchHistory)
}