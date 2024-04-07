package com.example.attractions.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.attractions.data.model.Photo
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {
    @Query("SELECT * FROM photos")
    fun getAll()  : Flow<List<Photo>>

    @Insert
    suspend fun insert(photo: Photo)

    @Query("DELETE FROM photos WHERE uri = :uri")
    suspend fun delete(uri: String)
}
