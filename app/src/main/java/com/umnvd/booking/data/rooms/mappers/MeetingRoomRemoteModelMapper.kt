package com.umnvd.booking.data.rooms.mappers

import com.umnvd.booking.data.rooms.models.MeetingRoomRemoteModel
import com.umnvd.booking.data.rooms.models.MeetingRoomFormRemoteModel
import com.umnvd.booking.domain.rooms.models.MeetingRoomModel
import com.umnvd.booking.domain.rooms.models.MeetingRoomFormModel

object MeetingRoomRemoteModelMapper {

    fun dtoToDomain(model: MeetingRoomRemoteModel) = MeetingRoomModel(
        uid = model.uid,
        name = model.name,
        address = model.address,
    )

    fun formDomainToDto(model: MeetingRoomFormModel) = MeetingRoomFormRemoteModel(
        name = model.name,
        address = model.address,
    )
}