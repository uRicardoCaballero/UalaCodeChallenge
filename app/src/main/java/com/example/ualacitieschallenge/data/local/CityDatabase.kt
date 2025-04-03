package com.example.ualacitieschallenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ualacitieschallenge.data.model.CityEntity

@Database(
    entities = [CityEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CityDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
}