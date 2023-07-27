package com.umnvd.booking.data.mappers

import com.umnvd.booking.data.models.UserDTO
import com.umnvd.booking.domain.models.User

object UserDtoMapper {

    fun dtoToDomain(obj: UserDTO) = User(
        uid = obj.uid,
        nickname = obj.nickname,
        fullName = obj.fullName,
        photoUrl = obj.photoUrl,
    )
}