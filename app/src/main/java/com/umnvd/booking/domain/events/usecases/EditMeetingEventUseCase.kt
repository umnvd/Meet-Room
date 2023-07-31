package com.umnvd.booking.domain.events.usecases

import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.domain.AppException
import com.umnvd.booking.domain.events.models.MeetingEventFormModel
import com.umnvd.booking.domain.events.repositories.MeetingEventsRepository
import javax.inject.Inject

class EditMeetingEventUseCase @Inject constructor(
    private val meetingEventsRepository: MeetingEventsRepository,
) {

    suspend operator fun invoke(params: Params): Result<Unit, AppException> {
        return try {
            meetingEventsRepository.editEvent(params.uid, params.form)
            Result.Success(Unit)
        } catch (e: AppException) {
            Result.Error(e)
        }
    }

    data class Params(
        val uid: String,
        val form: MeetingEventFormModel,
    )
}