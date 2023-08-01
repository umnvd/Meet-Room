package com.umnvd.booking.data.rooms.mappers

import com.umnvd.booking.data.common.mappers.DateTimeMapper
import com.umnvd.booking.data.rooms.models.MeetingRoomFormRemoteModel
import com.umnvd.booking.data.rooms.models.MeetingRoomRemoteModel
import com.umnvd.booking.domain.rooms.models.MeetingRoomFormModel
import com.umnvd.booking.domain.rooms.models.MeetingRoomModel

object MeetingRoomRemoteModelMapper {

    fun remoteToDomain(model: MeetingRoomRemoteModel) = MeetingRoomModel(
        uid = model.uid,
        name = model.name,
        address = model.address,
        createdAt = DateTimeMapper.utcStringToLdt(model.createdAt),
    )

    fun formDomainToRemote(model: MeetingRoomFormModel) = MeetingRoomFormRemoteModel(
        name = model.name,
        address = model.address,
    )
}