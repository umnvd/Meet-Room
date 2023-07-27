package com.umnvd.booking.data.mappers

import com.umnvd.booking.data.models.MeetingRoomDTO
import com.umnvd.booking.data.models.MeetingRoomFormDTO
import com.umnvd.booking.domain.models.MeetingRoom
import com.umnvd.booking.domain.models.MeetingRoomForm

object MeetingRoomDtoMapper {

    fun dtoToDomain(obj: MeetingRoomDTO) = MeetingRoom(
        uid = obj.uid,
        name = obj.name,
        address = obj.address,
    )

    fun formDomainToDto(obj: MeetingRoomForm) = MeetingRoomFormDTO(
        name = obj.name,
        address = obj.address,
    )
}