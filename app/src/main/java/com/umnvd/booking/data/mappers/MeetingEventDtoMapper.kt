package com.umnvd.booking.data.mappers

import com.umnvd.booking.data.models.MeetingEventDTO
import com.umnvd.booking.data.models.MeetingEventFormDTO
import com.umnvd.booking.domain.models.MeetingEvent
import com.umnvd.booking.domain.models.MeetingEventForm
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object MeetingEventDtoMapper {

    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    fun dtoToDomain(obj: MeetingEventDTO) = MeetingEvent(
        uid = obj.uid,
        title = obj.title,
        description = obj.description,
        startAt = obj.startAt.toLdt(),
        endAt = obj.endAt.toLdt(),
        room = MeetingRoomDtoMapper.dtoToDomain(obj.room),
        participants = obj.participants.map(UserDtoMapper::dtoToDomain)
    )

    fun formDomainToDto(obj: MeetingEventForm) = MeetingEventFormDTO(
        title = obj.title,
        description = obj.description,
        startAt = obj.startAt.toUtc(),
        endAt = obj.endAt.toUtc(),
        roomUid = obj.room!!.uid,
        participantsUids = obj.participants.map { it.uid }
    )

    private fun String.toLdt() = OffsetDateTime
        .parse(this, formatter)
        .atZoneSameInstant(ZoneOffset.systemDefault())
        .toLocalDateTime()

    private fun LocalDateTime.toUtc() = formatter.format(
        atOffset(
            ZoneOffset.systemDefault().rules.getOffset(this)
        ).atZoneSameInstant(ZoneOffset.UTC)
    )
}