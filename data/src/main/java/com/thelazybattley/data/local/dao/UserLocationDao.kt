package com.thelazybattley.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thelazybattley.data.local.entity.UserLocationEntity
import com.thelazybattley.data.local.entity.UserLocationEntity.Companion.TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface UserLocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: UserLocationEntity)

    @Query("SELECT * FROM $TABLE_NAME limit 1")
    fun getLocation() : Flow<UserLocationEntity?>

}
