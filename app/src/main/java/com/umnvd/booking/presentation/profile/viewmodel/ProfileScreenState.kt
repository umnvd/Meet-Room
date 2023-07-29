package com.umnvd.booking.presentation.profile.viewmodel

import com.umnvd.booking.domain.users.models.UserModel

data class ProfileScreenState(
    val loading: Boolean = false,
    val user: UserModel? = null,
    val signedOut: Boolean = false,
)
