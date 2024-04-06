package com.example.attractions.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.attractions.data.model.Photo

@Database(
    entities = [
        Photo::class
    ], version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}