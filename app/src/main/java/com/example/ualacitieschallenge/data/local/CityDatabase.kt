package com.example.ualacitieschallenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ualacitieschallenge.data.model.City

@Database(entities = [City::class], version = 1)
abstract class CityDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
}
