package com.thelazybattley.domain.usecase

import java.time.LocalDate

interface LocalDateFormatterUseCase {

    operator fun invoke(date: LocalDate, pattern : String) : String

}
