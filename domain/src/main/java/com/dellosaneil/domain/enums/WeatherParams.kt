package com.dellosaneil.domain.enums

enum class WeatherParams {
    TEMPERATURE_2M,
    RELATIVE_HUMIDITY_2M,
    APPARENT_TEMPERATURE,
    IS_DAY,
    PRECIPITATION,
    WEATHER_CODE,
    CLOUD_COVER,
    SURFACE_PRESSURE,
    WIND_SPEED_10M,
    WIND_DIRECTION_10M,
    TEMPERATURE_2M_MAX,
    TEMPERATURE_2M_MIN,
    SUNRISE,
    SUNSET,
    DAYLIGHT_DURATION,
    PRECIPITATION_PROBABILITY_MAX,
    PRECIPITATION_PROBABILITY,
    VISIBILITY
    ;

    companion object {
        fun currentWeatherParams() = listOf(
            TEMPERATURE_2M,
            RELATIVE_HUMIDITY_2M,
            APPARENT_TEMPERATURE,
            IS_DAY,
            PRECIPITATION,
            WEATHER_CODE,
            CLOUD_COVER,
            SURFACE_PRESSURE,
            WIND_DIRECTION_10M,
            WIND_SPEED_10M,
        ).joinToString(separator = ",")

        fun dailyWeatherForecast() = listOf(
            TEMPERATURE_2M_MAX,
            TEMPERATURE_2M_MIN,
            SUNRISE,
            SUNSET,
            DAYLIGHT_DURATION,
            PRECIPITATION_PROBABILITY_MAX,
            WEATHER_CODE
        ).joinToString(separator = ",")

        fun hourlyWeatherForecast() = listOf(
            TEMPERATURE_2M,
            PRECIPITATION_PROBABILITY,
            WEATHER_CODE,
            WIND_DIRECTION_10M,
            WIND_SPEED_10M,
            RELATIVE_HUMIDITY_2M,
            APPARENT_TEMPERATURE,
            VISIBILITY,
            CLOUD_COVER,
            SURFACE_PRESSURE,
            PRECIPITATION
        ).joinToString(separator = ",")
    }

    override fun toString(): String {
        return name.lowercase()
    }

}
