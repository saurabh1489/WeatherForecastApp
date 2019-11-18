package com.sample.weather.data.db

import androidx.room.TypeConverter

class ListToStringTypeConverter {
    @TypeConverter
    fun toString(list: List<String>): String {
        return list.joinToString(separator = ",")
    }

    @TypeConverter
    fun fromString(string: String): List<String> {
        return string.split(",").map { it }
    }
}