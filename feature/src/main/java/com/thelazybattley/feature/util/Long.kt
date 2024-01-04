package com.thelazybattley.feature.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

enum class DatePattern(val pattern: String) {

    DAY_DATE_MONTH(pattern = "EEEE, dd MMM"),
    DATE_MONTH(pattern = "dd MMM"),
    DAY(pattern = "EEEE"),
    DAY_SHORT(pattern = "EEE"),
    HOUR_MINUTES_MERIDIEM(pattern = "h:mm a"),
    HOURS(pattern = "HH"),
    DATE_MONTH_HOURS_MINUTES_MERIDIEM("dd MMM h:mm a"),
    DATE_MONTH_YEAR(pattern = "dd MMM yyyy")
}

fun Long.toDateString(pattern: DatePattern, timeZone: String): String {
    val formatter = DateTimeFormatter.ofPattern(pattern.pattern)
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.of(timeZone))
        .toLocalDateTime()
        .format(formatter)
}
