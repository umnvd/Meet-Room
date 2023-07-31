package com.umnvd.booking.domain.events.usecases

import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.domain.AppException
import com.umnvd.booking.domain.events.repositories.MeetingEventsRepository
import javax.inject.Inject

class DeleteMeetingEventUseCase @Inject constructor(
    private val meetingEventsRepository: MeetingEventsRepository,
) {

    suspend operator fun invoke(params: Params): Result<Unit, AppException> {
        val events = meetingEventsRepository.deleteEvent(params.uid)
        return Result.Success(Unit)
    }

    data class Params(val uid: String)
}