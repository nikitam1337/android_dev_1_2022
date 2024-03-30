package com.example.roomapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomapp.data.db.Word
import com.example.roomapp.data.db.WordDao

@Database(entities = [Word::class], version = 1)
abstract class WordDatabase: RoomDatabase() {
    abstract fun wordDao() : WordDao
}