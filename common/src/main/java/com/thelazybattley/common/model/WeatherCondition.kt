package com.thelazybattley.common.model

import androidx.annotation.DrawableRes
import com.thelazybattley.common.R

data class WeatherCondition(
    @DrawableRes val icon: Int,
    val description: String
) {
    companion object {
        fun toWeatherCondition(id: Int, isDay: Boolean): WeatherCondition {
            return when (id) {
                0 -> {
                    val icon = if (isDay) R.drawable.img_sunny else R.drawable.img_moon
                    WeatherCondition(
                        icon = icon,
                        description = "clear sky"
                    )
                }

                1 -> {
                    val icon = if (isDay) R.drawable.img_cloudy else R.drawable.img_cloudy_moon
                    WeatherCondition(
                        icon = icon,
                        description = "mainly clear"
                    )
                }

                2 -> {
                    val icon = if (isDay) R.drawable.img_cloudy else R.drawable.img_cloudy_moon
                    WeatherCondition(
                        icon = icon,
                        description = "partly cloudy"
                    )
                }

                3 -> {
                    val icon = if (isDay) R.drawable.img_cloudy else R.drawable.img_cloudy_moon
                    WeatherCondition(
                        icon = icon,
                        description = "overcast"
                    )
                }

                4 -> {
                    val icon = if (isDay) R.drawable.img_sunny else R.drawable.img_moon
                    WeatherCondition(
                        icon = icon,
                        description = "clear sky"
                    )
                }

                45, 48 -> {
                    WeatherCondition(
                        icon = R.drawable.img_foggy,
                        description = "fog"
                    )
                }

                51 -> {
                    WeatherCondition(
                        icon = R.drawable.img_light_rain,
                        description = "light drizzle"
                    )
                }

                53 -> {
                    WeatherCondition(
                        icon = R.drawable.img_light_rain,
                        description = "moderate drizzle"
                    )
                }

                55 -> {
                    WeatherCondition(
                        icon = R.drawable.img_light_rain,
                        description = "dense drizzle"
                    )
                }

                56 -> {
                    WeatherCondition(
                        icon = R.drawable.img_snow,
                        description = "light freezing drizzle"
                    )
                }

                57 -> {
                    WeatherCondition(
                        icon = R.drawable.img_snow,
                        description = "dense freezing drizzle"
                    )
                }

                61 -> {
                    WeatherCondition(
                        icon = R.drawable.img_light_rain,
                        description = "slight rain"
                    )
                }

                63 -> {
                    WeatherCondition(
                        icon = R.drawable.img_light_rain,
                        description = "moderate rain"
                    )
                }

                65 -> {
                    WeatherCondition(
                        icon = R.drawable.img_light_rain,
                        description = "heavy rain"
                    )
                }

                66 -> {
                    WeatherCondition(
                        icon = R.drawable.img_snow,
                        description = "light freezing rain"
                    )
                }

                67 -> {
                    WeatherCondition(
                        icon = R.drawable.img_snow,
                        description = "heavy freezing rain"
                    )
                }

                71 -> {
                    WeatherCondition(
                        icon = R.drawable.img_snow,
                        description = "light snow fall"
                    )
                }

                73 -> {
                    WeatherCondition(
                        icon = R.drawable.img_snow,
                        description = "moderate snow fall"
                    )
                }

                75 -> {
                    WeatherCondition(
                        icon = R.drawable.img_snow,
                        description = "heavy snow fall"
                    )
                }

                77 -> {
                    WeatherCondition(
                        icon = R.drawable.img_snow,
                        description = "snow grains"
                    )
                }

                80 -> {
                    WeatherCondition(
                        icon = R.drawable.img_light_rain,
                        description = "light rain shower"
                    )
                }

                81 -> {
                    WeatherCondition(
                        icon = R.drawable.img_light_rain,
                        description = "moderate rain shower"
                    )
                }

                82 -> {
                    WeatherCondition(
                        icon = R.drawable.img_light_rain,
                        description = "violent rain shower"
                    )
                }

                85 -> {
                    WeatherCondition(
                        icon = R.drawable.img_snow,
                        description = "light snow shower"
                    )
                }

                86 -> {
                    WeatherCondition(
                        icon = R.drawable.img_snow,
                        description = "heavy rain shower"
                    )
                }

                95 -> {
                    WeatherCondition(
                        icon = R.drawable.img_thunderstorm,
                        description = "thunderstorm"
                    )
                }

                else -> {
                    WeatherCondition(
                        icon = R.drawable.img_light_rain,
                        description = "light rain"
                    )
                }
            }

        }
    }
}
