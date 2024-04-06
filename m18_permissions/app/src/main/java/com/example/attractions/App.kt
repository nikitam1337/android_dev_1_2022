package com.example.attractions

import android.app.Application
import androidx.room.Room
import com.example.attractions.data.db.AppDatabase
import com.example.attractions.data.db.MIGRATION_1_2
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "db"
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }
}

