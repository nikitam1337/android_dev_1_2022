package com.example.attractions

import android.app.Application
import androidx.room.Room
import com.example.attractions.data.db.AppDatabase
import com.example.attractions.data.db.MIGRATION_1_2
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()

        MapKitFactory.setApiKey("6d2960cb-dc4c-4276-863a-271e8a28b411")

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "db"
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }
}

