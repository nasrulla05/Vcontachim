package com.akhbulatov.vcontachim.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "historyTable")
data class SearchHistoryModel(
    @PrimaryKey

    @ColumnInfo(name = "name_bd")
    val name: String

)