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
import com.umnvd.booking.domain.events.repositories.MeetingEventsRepository
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class ValidateMeetingEventUseCase @Inject constructor(
    private val eventsRepository: MeetingEventsRepository,
) {

    suspend operator fun invoke(params: Params): Result<Unit, MeetingEventValidationError> {
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
        Log.d("EVENT_DATE_TIME", "$duration")
        val durationError = when {
            duration < 0 -> EventNegativeDurationException()
            duration < 30 -> EventMinDurationException(duration)
            duration > 1440 -> EventMaxDurationException(duration)
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
            val events = eventsRepository.allEvents()
            events.forEach { params.form.checkIntersection(it) }
        } catch (e: EventStartIntersectionException) {
            startError = e
        } catch (e: EventEndIntersectionException) {
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

    data class Params(val form: MeetingEventFormModel)

    private fun MeetingEventFormModel.checkIntersection(event: MeetingEventModel) {
        if (startAt < event.startAt && endAt > event.startAt)
            throw EventStartIntersectionException(event.endAt)
        if (endAt > event.endAt && startAt < event.endAt)
            throw EventEndIntersectionException(event.startAt)
        if (startAt < event.startAt && endAt > event.endAt)
            throw EventOverlappingException(event.startAt, event.endAt)
    }

}