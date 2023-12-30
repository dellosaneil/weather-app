package com.thelazybattley.domain.usecase.impl

import com.thelazybattley.domain.usecase.LocalDateFormatterUseCase
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class LocalDateFormatterUseCaseImpl @Inject constructor() : LocalDateFormatterUseCase {

    companion object {
        const val YEAR_MONTH_DAY = "yyyy-MM-dd"
    }

    override fun invoke(date: LocalDate, pattern: String): String {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return date.format(formatter)
    }
}
