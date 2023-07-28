package com.umnvd.booking.data.events.mappers

import com.umnvd.booking.data.rooms.mappers.MeetingRoomRemoteModelMapper
import com.umnvd.booking.data.users.mappers.UserRemoteModelMapper
import com.umnvd.booking.data.events.models.MeetingEventRemoteModel
import com.umnvd.booking.data.events.models.MeetingEventFormRemoteModel
import com.umnvd.booking.domain.events.models.MeetingEventModel
import com.umnvd.booking.domain.events.models.MeetingEventFormModel
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object MeetingEventRemoteModelMapper {

    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    fun dtoToDomain(model: MeetingEventRemoteModel) = MeetingEventModel(
        uid = model.uid,
        title = model.title,
        description = model.description,
        startAt = model.startAt.toLdt(),
        endAt = model.endAt.toLdt(),
        room = MeetingRoomRemoteModelMapper.dtoToDomain(model.room),
        participants = model.participants.map(UserRemoteModelMapper::dtoToDomain)
    )

    fun formDomainToDto(model: MeetingEventFormModel) = MeetingEventFormRemoteModel(
        title = model.title,
        description = model.description,
        startAt = model.startAt.toUtc(),
        endAt = model.endAt.toUtc(),
        roomUid = model.room!!.uid,
        participantsUids = model.participants.map { it.uid }
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