package com.thelazybattley.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.thelazybattley.data.local.entity.UserLocationEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class UserLocationEntity(
    @PrimaryKey val id : Int = 1,
    val latitude: Double,
    val longitude: Double,
    val address: String
) {
    companion object {
        const val TABLE_NAME = "user_location"
    }
}
