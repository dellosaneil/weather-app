package com.thelazybattley.domain.local.usecase.impl

import com.thelazybattley.domain.local.model.userlocation.UserLocation
import com.thelazybattley.domain.local.usecase.InsertLocationUseCase
import com.thelazybattley.domain.repository.WeatherRepository
import javax.inject.Inject

class InsertLocationUseCaseImpl @Inject constructor(
    private val repository: WeatherRepository
): InsertLocationUseCase {


    override suspend fun invoke(userLocation: UserLocation) = repository.insertLocation(userLocation = userLocation)

}
