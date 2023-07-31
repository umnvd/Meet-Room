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
import com.umnvd.booking.domain.EventEndIntersectionException
import com.umnvd.booking.domain.EventParticipantsRequiredException
import com.umnvd.booking.domain.EventRoomRequiredException
import com.umnvd.booking.domain.EventStartIntersectionException
import com.umnvd.booking.domain.EventTitleLengthException
import com.umnvd.booking.domain.EventTitleRequiredException
import com.umnvd.booking.domain.NetworkException
import com.umnvd.booking.domain.PasswordInvalidException
import com.umnvd.booking.domain.PasswordMinLengthException
import com.umnvd.booking.domain.PasswordRequiredException
import com.umnvd.booking.domain.RoomAddressRequiredException
import com.umnvd.booking.domain.RoomNameLengthException
import com.umnvd.booking.domain.RoomNameRequiredException
import com.umnvd.booking.domain.UnauthorizedException
import com.umnvd.booking.domain.UnknownException
import java.time.format.DateTimeFormatter
import java.util.Locale
import androidx.compose.ui.text.intl.Locale.Companion as AndroidLocale


val AppException.text: String
    @Composable
    get() = textFromResources(LocalContext.current.resources)

fun AppException.getText(context: Context): String = textFromResources(context.resources)

private fun AppException.textFromResources(resources: Resources): String = when (this) {
    is UnknownException -> resources.getString(R.string.unknown_error)
    is NetworkException -> resources.getString(R.string.network_error)
    is UnauthorizedException -> resources.getString(R.string.unauthorized_error)
    is EmailRequiredException -> resources.getString(R.string.email_required_error)
    is EmailInvalidException -> resources.getString(R.string.email_invalid_error)
    is EmailNotRegisteredException -> resources.getString(R.string.email_not_registered_error)
    is PasswordRequiredException -> resources.getString(R.string.password_required_error)
    is PasswordMinLengthException -> resources.getString(
        R.string.password_too_short_error,
        minLength
    )

    is PasswordInvalidException -> resources.getString(R.string.password_invalid_error)
    is RoomNameRequiredException -> resources.getString(R.string.room_name_required_error)
    is RoomNameLengthException -> resources.getString(R.string.room_name_length_error, minLength)
    is RoomAddressRequiredException -> resources.getString(R.string.room_address_required_error)
    is EventTitleRequiredException -> resources.getString(R.string.event_title_required_error)
    is EventTitleLengthException -> resources.getString(
        R.string.event_title_length_error,
        minLength
    )

    is EventRoomRequiredException -> resources.getString(R.string.event_room_required_error)
    is EventParticipantsRequiredException -> resources.getString(R.string.event_participants_required_error)
    is EventStartIntersectionException -> {
        val formattedDateTime = dateTime.format(
            DateTimeFormatter.ofPattern(
                "dd.MM.yyyy HH:mm",
                Locale.forLanguageTag(AndroidLocale.current.toLanguageTag())
            )
        )
        resources.getString(R.string.event_start_intersection_error, formattedDateTime)
    }

    is EventEndIntersectionException -> {
        val formattedDateTime = dateTime.format(
            DateTimeFormatter.ofPattern(
                "dd.MM.yyyy HH:mm",
                Locale.forLanguageTag(AndroidLocale.current.toLanguageTag())
            )
        )
        resources.getString(R.string.event_end_intersection_error, formattedDateTime)
    }
}
