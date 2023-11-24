package com.thelazybattley.data.network.service

import com.thelazybattley.data.network.response.current.CurrentWeatherDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("current.json")
    suspend fun getCurrentWeather(@Query("q") city: String): CurrentWeatherDataResponse

}
