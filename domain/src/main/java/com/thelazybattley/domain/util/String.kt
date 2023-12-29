package com.thelazybattley.domain.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException


fun String.toEpoch(timeZone: String): Int {
    return try {
        LocalDateTime
            .parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))
            .atZone(ZoneId.of(timeZone))
            .toEpochSecond()
            .toInt()
    } catch (e: DateTimeParseException) {
        LocalDate
            .parse(this)
            .atStartOfDay()
            .atZone(ZoneId.of(timeZone))
            .toEpochSecond().toInt()
    }
}

