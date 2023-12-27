package com.thelazybattley.domain.usecase.impl

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import com.thelazybattley.domain.usecase.GetAddress
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject

class GetAddressImpl @Inject constructor(
   @ApplicationContext context: Context
) : GetAddress {

    private val geocoder = Geocoder(context, Locale.getDefault())

    override suspend fun invoke(coordinates: LatLng): Result<Address> {
        return try {
            val address = geocoder.getFromLocation(coordinates.latitude, coordinates.longitude, 1).orEmpty().first()
            Result.success(address)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
