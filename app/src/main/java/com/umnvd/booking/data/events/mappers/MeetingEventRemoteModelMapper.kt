package com.umnvd.booking.data.events.mappers

import com.umnvd.booking.data.common.mappers.DateTimeMapper
import com.umnvd.booking.data.events.models.MeetingEventFormRemoteModel
import com.umnvd.booking.data.events.models.MeetingEventRemoteModel
import com.umnvd.booking.data.rooms.mappers.MeetingRoomRemoteModelMapper
import com.umnvd.booking.data.users.mappers.UserRemoteModelMapper
import com.umnvd.booking.domain.events.models.MeetingEventFormModel
import com.umnvd.booking.domain.events.models.MeetingEventModel

object MeetingEventRemoteModelMapper {

    fun dtoToDomain(model: MeetingEventRemoteModel) = MeetingEventModel(
        uid = model.uid,
        title = model.title,
        description = model.description,
        startAt = DateTimeMapper.utcStringToLdt(model.startAt),
        endAt = DateTimeMapper.utcStringToLdt(model.endAt),
        room = MeetingRoomRemoteModelMapper.dtoToDomain(model.room),
        participants = model.participants.map(UserRemoteModelMapper::dtoToDomain)
    )

    fun formDomainToDto(model: MeetingEventFormModel) = MeetingEventFormRemoteModel(
        title = model.title,
        description = model.description,
        startAt = DateTimeMapper.ldtToUtcString(model.startAt),
        endAt = DateTimeMapper.ldtToUtcString(model.endAt),
        roomUid = model.room!!.uid,
        participantsUids = model.participants.map { it.uid }
    )
}