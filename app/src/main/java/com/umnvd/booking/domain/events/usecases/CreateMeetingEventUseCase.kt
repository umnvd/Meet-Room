package com.umnvd.booking.domain.events.usecases

import android.util.Log
import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.domain.AppException
import com.umnvd.booking.domain.events.models.MeetingEventFormModel
import com.umnvd.booking.domain.events.repositories.MeetingEventsRepository
import javax.inject.Inject

class CreateMeetingEventUseCase @Inject constructor(
    private val meetingEventsRepository: MeetingEventsRepository,
) {

    suspend operator fun invoke(params: Params): Result<Unit, AppException> {
        return try {
            Log.d("CREATE_MEETING_EVENT", "params: $params")
            meetingEventsRepository.createEvent(params.form)
            Log.d("CREATE_MEETING_EVENT", "us return")
            Result.Success(Unit)
        } catch (e: AppException) {
            Result.Error(e)
        }
    }

    data class Params(val form: MeetingEventFormModel)
}