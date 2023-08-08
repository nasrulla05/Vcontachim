package com.akhbulatov.vcontachim.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class HistoryUser(
    @PrimaryKey

    @ColumnInfo(name = "id_db")
    val id:Int,

    @ColumnInfo(name = "name_bd")
    val name: String

) : Serializable