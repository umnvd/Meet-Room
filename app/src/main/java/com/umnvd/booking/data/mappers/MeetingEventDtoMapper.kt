package com.umnvd.booking.data.mappers

import com.umnvd.booking.data.models.MeetingEventDTO
import com.umnvd.booking.data.models.MeetingEventFormDTO
import com.umnvd.booking.domain.models.MeetingEvent
import com.umnvd.booking.domain.models.MeetingEventForm
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object MeetingEventDtoMapper {

    private const val DATE_TIME_PATTERN = "dd-MM-yyyy HH:mm:ss"
    private val formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)

    fun dtoToDomain(obj: MeetingEventDTO) = MeetingEvent(
        uid = obj.uid,
        title = obj.title,
        description = obj.description,
        startAt = LocalDateTime.parse(obj.startAt, formatter),
        endAt = LocalDateTime.parse(obj.endAt, formatter),
        room = MeetingRoomDtoMapper.dtoToDomain(obj.room),
        participants = obj.participants.map(UserDtoMapper::dtoToDomain)
    )

    fun formDomainToDto(obj: MeetingEventForm) = MeetingEventFormDTO(
        title = obj.title,
        description = obj.description,
        startAt = formatter.format(obj.startAt),
        endAt = formatter.format(obj.endAt),
        roomUid = obj.room!!.uid,
        participantsUids = obj.participants.map { it.uid }
    )
}