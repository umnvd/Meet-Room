package com.umnvd.booking.core.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.umnvd.booking.R
import com.umnvd.booking.domain.AppException
import com.umnvd.booking.domain.EmailInvalidException
import com.umnvd.booking.domain.EmailNotRegisteredException
import com.umnvd.booking.domain.EmailRequiredException
import com.umnvd.booking.domain.NetworkException
import com.umnvd.booking.domain.PasswordInvalidException
import com.umnvd.booking.domain.PasswordMinLengthException
import com.umnvd.booking.domain.PasswordRequiredException
import com.umnvd.booking.domain.UnauthorizedException

val AppException.text: String
    @Composable
    get() = when (this) {
        is NetworkException -> stringResource(R.string.network_error)
        is UnauthorizedException -> "Unauthorized"
        is EmailRequiredException -> stringResource(R.string.email_required_error)
        is EmailInvalidException -> stringResource(R.string.email_invalid_error)
        is EmailNotRegisteredException -> stringResource(R.string.email_not_registered_error)
        is PasswordRequiredException -> stringResource(R.string.password_required_error)
        is PasswordMinLengthException -> stringResource(
            R.string.password_too_short_error,
            minLength
        )

        is PasswordInvalidException -> stringResource(R.string.password_invalid_error)
    }
