package com.thelazybattley.common.util

import androidx.annotation.DrawableRes
import com.thelazybattley.common.R

enum class WeatherIconEnum(@DrawableRes val iconRes: Int) {
    MOON(iconRes = R.drawable.img_moon),
    SUNNY(iconRes = R.drawable.img_sunny),
    CLOUDY_SUN(iconRes = R.drawable.img_scattered_clouds),
    SCATTERED_CLOUDS(iconRes = R.drawable.img_scattered_clouds),
    BROKEN_CLOUDS(iconRes = R.drawable.img_scattered_clouds),
    SHOWER_RAIN(iconRes = R.drawable.img_shower_rain),
    RAIN(iconRes = R.drawable.img_light_rain),
    THUNDERSTORM(iconRes = R.drawable.img_thunderstorm),
    SNOW(iconRes = R.drawable.img_snow),
    MIST_SUN(iconRes = R.drawable.img_mist_sun),
    MIST_MOON(iconRes = R.drawable.ic_expand),
    CLOUDY_MOON(iconRes = R.drawable.img_cloudy_moon),
    SHOWER_RAIN_MOON(iconRes = R.drawable.img_moon_shower_rain);

    companion object {
        fun toWeatherIcon(icon: String): WeatherIconEnum {
            return when (icon) {
                "01d" -> SUNNY
                "01n" -> MOON
                "02d" -> CLOUDY_SUN
                "02n" -> CLOUDY_MOON
                "03d" -> SCATTERED_CLOUDS
                "03n" -> CLOUDY_MOON
                "04d" -> BROKEN_CLOUDS
                "04n" -> CLOUDY_MOON
                "09d" -> SHOWER_RAIN
                "09n" -> SHOWER_RAIN_MOON
                "10d" -> RAIN
                "10n" -> RAIN
                "11d" -> THUNDERSTORM
                "11n" -> THUNDERSTORM
                "13d" -> SNOW
                "13n" -> SNOW
                "50d" -> MIST_SUN
                "50n" -> MIST_MOON
                else -> SUNNY
            }
        }
    }
}
