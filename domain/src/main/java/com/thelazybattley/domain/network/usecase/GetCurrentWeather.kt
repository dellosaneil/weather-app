package com.thelazybattley.domain.network.usecase

import com.thelazybattley.domain.network.schema.current.CurrentWeatherDataSchema

interface GetCurrentWeather {

    suspend operator fun invoke(latitude: String, longitude: String) : Result<CurrentWeatherDataSchema>
}
