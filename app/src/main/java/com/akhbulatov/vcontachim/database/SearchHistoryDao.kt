package com.akhbulatov.vcontachim.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SearchHistoryDao {

    @Query("select*from HistoryUser")
    suspend fun getAllHistory():List<HistoryUser>

//    @Query("delete from historyuser")
//    suspend fun getDeleteHistory(): List<HistoryUser>

    @Insert
    suspend fun addHistory(history: HistoryUser)

    @Delete
    suspend fun deleteHistory(history: HistoryUser)
}