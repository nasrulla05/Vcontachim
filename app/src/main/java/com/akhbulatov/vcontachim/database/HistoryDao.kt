package com.akhbulatov.vcontachim.database

import androidx.room.*

@Dao
interface HistoryDao {

    @Query("select*from historyTable")
    suspend fun getAllHistory(): List<HistoryUser>

    @Query("delete from historyTable")
    suspend fun deleteHistoryList()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHistory(history: HistoryUser)

    @Delete
    suspend fun deleteHistoryItem(history: HistoryUser)
}