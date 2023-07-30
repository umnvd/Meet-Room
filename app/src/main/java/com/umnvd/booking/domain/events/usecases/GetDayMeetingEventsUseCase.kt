package com.umnvd.booking.domain.events.usecases

import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.domain.AppException
import com.umnvd.booking.domain.events.models.MeetingEventModel
import com.umnvd.booking.domain.events.repositories.MeetingEventsRepository
import java.time.LocalDate
import javax.inject.Inject

class GetDayMeetingEventsUseCase @Inject constructor(
    private val meetingEventsRepository: MeetingEventsRepository,
) {

    suspend operator fun invoke(params: Params): Result<List<MeetingEventModel>, AppException> {
        val events = meetingEventsRepository
            .allEvents()
            .filter {
                it.startAt.toLocalDate() == params.date
                        || it.endAt.toLocalDate() == params.date
            }
        return Result.Success(events)
    }

    data class Params(val date: LocalDate)
}