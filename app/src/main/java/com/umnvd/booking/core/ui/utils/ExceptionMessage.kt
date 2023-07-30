package com.umnvd.booking.core.ui.utils

import android.content.Context
import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
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
    get() = textFromResources(LocalContext.current.resources)

fun AppException.getText(context: Context): String = textFromResources(context.resources)

private fun AppException.textFromResources(resources: Resources): String = when (this) {
    is NetworkException -> resources.getString(R.string.network_error)
    is UnauthorizedException -> "Unauthorized" // TODO
    is EmailRequiredException -> resources.getString(R.string.email_required_error)
    is EmailInvalidException -> resources.getString(R.string.email_invalid_error)
    is EmailNotRegisteredException -> resources.getString(R.string.email_not_registered_error)
    is PasswordRequiredException -> resources.getString(R.string.password_required_error)
    is PasswordMinLengthException -> resources.getString(
        R.string.password_too_short_error,
        minLength
    )

    is PasswordInvalidException -> resources.getString(R.string.password_invalid_error)
    else -> "Unknown error"
}
