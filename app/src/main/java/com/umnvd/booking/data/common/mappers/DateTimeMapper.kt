package com.umnvd.booking.data.common.mappers

import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object DateTimeMapper {

    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    fun utcStringToLdt(dateTime: String) = OffsetDateTime
        .parse(dateTime, formatter)
        .atZoneSameInstant(ZoneOffset.systemDefault())
        .toLocalDateTime()

    fun ldtToUtcString(dateTime: LocalDateTime) = formatter.format(
        dateTime.atOffset(
            ZoneOffset.systemDefault().rules.getOffset(dateTime)
        ).atZoneSameInstant(ZoneOffset.UTC)
    )
}