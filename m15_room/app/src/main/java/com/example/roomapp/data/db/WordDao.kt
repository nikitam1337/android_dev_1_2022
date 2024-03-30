package com.example.roomapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface WordDao {
    @Query("SELECT * FROM word LIMIT 5")
    fun getFirst5Words(): Flow<List<Word>>

    @Insert
    suspend fun addNewWord(word: Word)

    @Query("SELECT * FROM word WHERE content LIKE :searchWord")
    suspend fun getSearchWord(searchWord: String): Word?

    @Query("DELETE FROM word")
    suspend fun deleteAll()

    @Query("UPDATE word SET count = count+1 WHERE content  LIKE :newWord")
    suspend fun update(newWord: String)
}