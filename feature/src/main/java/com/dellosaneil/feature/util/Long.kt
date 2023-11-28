package com.dellosaneil.feature.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

enum class DatePattern(val pattern: String) {

    DAY_DATE_MONTH(pattern = "EEEE, dd MMM"),
    DATE_MONTH(pattern = "dd MMM"),
    DAY(pattern = "EEEE"),
    DAY_SHORT(pattern = "EEE"),
    HOUR_MINUTES_MERIDIEM(pattern = "h:mm a")
}

fun Long.toDateString(pattern: DatePattern): String {
    val formatter = DateTimeFormatter.ofPattern(pattern.pattern)
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
        .format(formatter)
}
