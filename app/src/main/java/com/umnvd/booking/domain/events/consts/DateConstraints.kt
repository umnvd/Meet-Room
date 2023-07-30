package com.umnvd.booking.domain.events.consts

import java.time.LocalDate

val MIN_DATE: LocalDate = LocalDate.of(2023, 1, 1)
val MAX_DATE: LocalDate = MIN_DATE.plusDays(365)