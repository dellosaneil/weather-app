package com.thelazybattley.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thelazybattley.data.local.dao.UserLocationDao
import com.thelazybattley.data.local.entity.UserLocationEntity

@Database(entities = [UserLocationEntity::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "weatherapp"
    }


    abstract fun locationDao(): UserLocationDao
}
