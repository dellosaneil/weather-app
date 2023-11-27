package com.dellosaneil.domain.network.usecase

import com.dellosaneil.domain.network.schema.current.CurrentWeatherDataSchema

interface GetCurrentWeather {

    suspend operator fun invoke(latitude: String, longitude: String) : Result<CurrentWeatherDataSchema>
}
