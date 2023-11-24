package com.dellosaneil.domain.network.usecase

import com.dellosaneil.domain.network.schema.CurrentWeatherDataSchema

interface GetCurrentWeather {

    suspend operator fun invoke(city: String) : Result<CurrentWeatherDataSchema>
}
