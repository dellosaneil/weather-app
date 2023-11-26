package com.dellosaneil.feature.model.currentweather

data class CurrentWeatherData(
    val current: CurrentWeatherCurrent,
    val location: CurrentWeatherLocation
) {
    companion object {
        fun dummyData() = CurrentWeatherData(
            current = CurrentWeatherCurrent(
                cloud = 0,
                feelslikeC = 32.4,
                humidity = 45,
                isDay = true,
                lastUpdated = "07/10/1995",
                lastUpdatedEpoch = 1700976823,
                precipIn = 32.1,
                precipMm = 22.2,
                tempC = 43.0,
                windKph = 32.1,
                condition = CurrentWeatherCondition(
                    icon = "https://cdn.weatherapi.com/weather/64x64/night/116.png",
                    text = "Partly Cloudy"
                )
            ),
            location = CurrentWeatherLocation(
                country = "Canada",
                name = "Ottawa",
                region = "Ontario",
                tzId = "America/Toronto",
                localTimeMillis = 1700976858000
            )
        )
    }
}

