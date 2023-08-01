package com.umnvd.booking.data.users.mappers

import com.umnvd.booking.data.users.models.UserRemoteModel
import com.umnvd.booking.domain.users.models.UserModel

object UserRemoteModelMapper {

    fun remoteToDomain(obj: UserRemoteModel) = UserModel(
        uid = obj.uid,
        nickname = obj.nickname,
        fullName = obj.fullName,
        photoUrl = obj.photoUrl,
    )
}