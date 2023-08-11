package com.akhbulatov.vcontachim.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SearchHistoryModel::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): SearchHistoryDao
}