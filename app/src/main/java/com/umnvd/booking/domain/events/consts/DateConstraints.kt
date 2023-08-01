package com.umnvd.booking.domain.events.consts

import java.time.LocalDate
import java.time.temporal.ChronoUnit

val MIN_DATE: LocalDate = LocalDate.of(2023, 1, 1)
val MAX_DATE: LocalDate = LocalDate.of(2023, 12, 31)
val DAYS_COUNT = ChronoUnit.DAYS.between(MIN_DATE, MAX_DATE).toInt() + 1