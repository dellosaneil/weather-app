package com.thelazybattley.domain.local.usecase

import com.thelazybattley.domain.local.model.userlocation.UserLocation
import kotlinx.coroutines.flow.Flow

interface GetLocationUseCase {

    suspend operator fun invoke() : Flow<UserLocation?>

}
