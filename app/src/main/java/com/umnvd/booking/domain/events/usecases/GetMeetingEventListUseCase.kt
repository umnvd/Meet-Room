package com.umnvd.booking.domain.events.usecases

import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.domain.AppException
import com.umnvd.booking.domain.events.models.MeetingEventModel
import com.umnvd.booking.domain.events.repositories.MeetingEventsRepository
import javax.inject.Inject

class GetMeetingEventListUseCase @Inject constructor(
    private val meetingEventsRepository: MeetingEventsRepository,
) {

    suspend operator fun invoke(): Result<List<MeetingEventModel>, AppException> {
        return try {
            val events = meetingEventsRepository.allEvents()
            Result.Success(events)
        } catch (e: AppException) {
            Result.Error(e)
        }
    }
}