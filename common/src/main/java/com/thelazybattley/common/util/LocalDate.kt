package com.thelazybattley.common.util

import java.time.LocalDate
import java.time.ZoneId

val LocalDate.toMillis
    get() = run {
        atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }
