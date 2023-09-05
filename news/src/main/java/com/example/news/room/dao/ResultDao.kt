package com.example.news.room.dao

import androidx.room.*
import com.example.news.model.ResultLocal


@Dao
interface ResultDao {
    @Query("SELECT * FROM ResultLocal")
    suspend fun getAll(): List<ResultLocal>?

    @Insert
    suspend fun insert(resultLocal: ResultLocal)

    @Delete
    suspend fun delete(resultLocal: ResultLocal)
}