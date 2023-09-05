package com.example.news.room

import androidx.room.TypeConverter
import com.example.news.net.response.Fields
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@ExperimentalSerializationApi
class DataConverter {
    @TypeConverter
    fun fromFields(str: String): Fields? {
        return Json.decodeFromString(str)
    }

    @TypeConverter
    fun stringFields(fields: Fields): String {
        return Json.encodeToString(fields)
    }

}