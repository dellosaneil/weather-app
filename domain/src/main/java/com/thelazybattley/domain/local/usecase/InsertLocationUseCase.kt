package com.thelazybattley.domain.local.usecase

import com.thelazybattley.domain.local.model.userlocation.UserLocation

interface InsertLocationUseCase {

    suspend operator fun invoke(userLocation: UserLocation)

}
