package com.example.news.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.news.room.dao.ResultDao
import com.example.news.model.ResultLocal
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@Database(entities = [ResultLocal::class], version = 1)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ResultDao(): ResultDao
}
