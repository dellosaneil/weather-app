package com.thelazybattley.domain.local.model.userlocation

import com.thelazybattley.data.local.entity.UserLocationEntity

data class UserLocation(
    val latitude: Double,
    val longitude: Double,
    val address: String
) {
    companion object {
        fun toEntity(
            location: UserLocation
        ) = UserLocationEntity(
            latitude = location.latitude,
            longitude = location.longitude,
            address = location.address
        )

        fun fromEntity(
            entity: UserLocationEntity
        ) = UserLocation(
            latitude = entity.latitude,
            longitude = entity.longitude,
            address = entity.address
        )
    }
}
