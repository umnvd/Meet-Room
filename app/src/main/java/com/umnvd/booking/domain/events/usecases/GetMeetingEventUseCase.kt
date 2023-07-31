package com.umnvd.booking.domain.events.usecases

import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.domain.AppException
import com.umnvd.booking.domain.events.models.MeetingEventModel
import com.umnvd.booking.domain.events.repositories.MeetingEventsRepository
import javax.inject.Inject

class GetMeetingEventUseCase @Inject constructor(
    private val meetingEventsRepository: MeetingEventsRepository,
) {

    suspend operator fun invoke(params: Params): Result<MeetingEventModel, AppException> {
        return try {
            val event = meetingEventsRepository.event(params.uid)
            Result.Success(event)
        } catch (e: AppException) {
            Result.Error(e)
        }
    }

    data class Params(val uid: String)
}