package com.akhbulatov.vcontachim.database

import androidx.room.*

@Dao
interface SearchHistoryDao {

    @Query("select*from historyTable")
    suspend fun getAllHistory(): List<SearchHistoryModel>

    @Query("delete from historyTable")
    suspend fun deleteHistoryList()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHistory(history: SearchHistoryModel)

    @Delete
    suspend fun deleteHistoryItem(history: SearchHistoryModel)
}