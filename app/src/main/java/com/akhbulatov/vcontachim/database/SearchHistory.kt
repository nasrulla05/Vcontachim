package com.akhbulatov.vcontachim.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity
class SearchHistory(

    @PrimaryKey

    @ColumnInfo(name = "id_db")
    val id:Int,

    @ColumnInfo(name = "name_bd")
    val name: String
) : Serializable
