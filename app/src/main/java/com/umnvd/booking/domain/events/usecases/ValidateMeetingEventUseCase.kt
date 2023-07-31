package com.umnvd.booking.domain.events.usecases

import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.domain.AppException
import com.umnvd.booking.domain.EventEndIntersectionException
import com.umnvd.booking.domain.EventParticipantsRequiredException
import com.umnvd.booking.domain.EventRoomRequiredException
import com.umnvd.booking.domain.EventStartIntersectionException
import com.umnvd.booking.domain.EventTitleLengthException
import com.umnvd.booking.domain.EventTitleRequiredException
import com.umnvd.booking.domain.events.models.MeetingEventFormModel
import com.umnvd.booking.domain.events.models.MeetingEventModel
import com.umnvd.booking.domain.events.models.MeetingEventValidationError
import com.umnvd.booking.domain.events.repositories.MeetingEventsRepository
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

        return if (titleError != null
            || roomError != null
            || participantsError != null
            || startError != null
            || endError != null
        ) {
            Result.Error(
                MeetingEventValidationError(
                    title = titleError,
                    room = roomError,
                    participants = participantsError,
                )
            )
        } else Result.Success(Unit)
    }

    data class Params(
        val form: MeetingEventFormModel,
    )

    private fun MeetingEventFormModel.checkIntersection(event: MeetingEventModel) {
        if (startAt < event.endAt) throw EventStartIntersectionException(event.endAt)
        if (endAt > event.startAt) throw EventEndIntersectionException(event.startAt)
    }

}