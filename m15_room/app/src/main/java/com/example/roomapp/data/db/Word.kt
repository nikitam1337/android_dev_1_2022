package com.example.roomapp.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word")
data class Word(
    @PrimaryKey
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "count")
    val count: Int
)
