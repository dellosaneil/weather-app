package com.thelazybattley.domain.usecase

import android.location.Address
import com.google.android.gms.maps.model.LatLng

interface GetAddress {

    suspend operator fun invoke(coordinates: LatLng) : Result<Address>

}
