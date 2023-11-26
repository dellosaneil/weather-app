package com.dellosaneil.feature.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

enum class DatePattern(val pattern: String) {

    DAY_DATE_MONTH(pattern = "EEEE, dd MMM")

}

fun Long.toDateString(pattern: DatePattern): String {
    val formatter = DateTimeFormatter.ofPattern(pattern.pattern)
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .format(formatter)
}
