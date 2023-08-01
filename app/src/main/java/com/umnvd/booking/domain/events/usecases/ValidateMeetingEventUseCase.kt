package com.umnvd.booking.domain.events.usecases

import android.util.Log
import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.domain.AppException
import com.umnvd.booking.domain.EventDifferentDatesException
import com.umnvd.booking.domain.EventEndIntersectionException
import com.umnvd.booking.domain.EventMaxDurationException
import com.umnvd.booking.domain.EventMinDurationException
import com.umnvd.booking.domain.EventNegativeDurationException
import com.umnvd.booking.domain.EventOverlappingException
import com.umnvd.booking.domain.EventParticipantsRequiredException
import com.umnvd.booking.domain.EventRoomRequiredException
import com.umnvd.booking.domain.EventStartIntersectionException
import com.umnvd.booking.domain.EventTitleLengthException
import com.umnvd.booking.domain.EventTitleRequiredException
import com.umnvd.booking.domain.events.models.MeetingEventFormModel
import com.umnvd.booking.domain.events.models.MeetingEventModel
import com.umnvd.booking.domain.events.models.MeetingEventValidationError
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class ValidateMeetingEventUseCase @Inject constructor() {

    data class Params(
        val form: MeetingEventFormModel,
        val events: List<MeetingEventModel>,
        val uid: String? = null,
    )

    operator fun invoke(params: Params): Result<Unit, MeetingEventValidationError> {
        val trimmedTitle = params.form.title.trim()

        val titleError = when {
            trimmedTitle.isEmpty() -> EventTitleRequiredException()
            trimmedTitle.length < 3 -> EventTitleLengthException(3)
            else -> null
        }

        val roomError = when (params.form.room) {
            null -> EventRoomRequiredException()
            else -> null
        }

        val participantsError = when {
            params.form.participants.isEmpty() -> EventParticipantsRequiredException()
            else -> null
        }

        val startAt = params.form.startAt
        val endAt = params.form.endAt

        val duration = ChronoUnit.MINUTES.between(startAt, endAt)
        val durationError = when {
            duration < 0 -> EventNegativeDurationException()
            duration < 30 -> EventMinDurationException(30)
            duration > 1440 -> EventMaxDurationException(1440)
            else -> null
        }

        val dateError = when {
            startAt.toLocalDate() == endAt.toLocalDate() -> null
            startAt.toLocalDate() == endAt.toLocalDate().minusDays(1) &&
                    endAt.toLocalTime() == LocalTime.of(0, 0) -> null

            else -> EventDifferentDatesException()
        }

        if (titleError != null
            || roomError != null
            || participantsError != null
            || dateError != null
            || durationError != null
        ) {
            return Result.Error(
                MeetingEventValidationError(
                    title = titleError,
                    room = roomError,
                    participants = participantsError,
                    endAt = durationError ?: dateError,
                )
            )
        }

        var startError: AppException? = null
        var endError: AppException? = null

        try {
            params.events.forEach { params.form.checkIntersection(it, params.uid) }
        } catch (e: EventStartIntersectionException) {
            startError = e
        } catch (e: AppException) {
            endError = e
        }

        return if (startError != null || endError != null) {
            Result.Error(
                MeetingEventValidationError(
                    startAt = startError,
                    endAt = endError,
                )
            )
        } else Result.Success(Unit)
    }

    private fun MeetingEventFormModel.checkIntersection(
        event: MeetingEventModel,
        uid: String? = null
    ) {
        Log.d(
            "EVENT_VALIDATION",
            "${uid} - ${event.uid}"
        )

        if (uid == event.uid) return
        if (startAt.toLocalDate() != event.startAt.toLocalDate()) return

        Log.d(
            "EVENT_VALIDATION",
            "${this.startAt}-${this.endAt} - ${event.startAt}-$${event.endAt}}"
        )

        if (startAt < event.startAt && endAt > event.startAt)
            throw EventStartIntersectionException(event.endAt)
        if (endAt > event.endAt && startAt < event.endAt)
            throw EventEndIntersectionException(event.startAt)
        if (startAt <= event.startAt && endAt >= event.endAt)
            throw EventOverlappingException(event.startAt, event.endAt)
    }

}