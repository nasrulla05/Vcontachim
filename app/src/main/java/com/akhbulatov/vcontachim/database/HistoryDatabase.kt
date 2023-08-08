package com.akhbulatov.vcontachim.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [HistoryUser::class],
    version = 1,
    exportSchema = true
)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDao(): SearchHistoryDao
}