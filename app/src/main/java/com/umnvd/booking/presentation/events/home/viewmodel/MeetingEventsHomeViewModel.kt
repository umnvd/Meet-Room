package com.umnvd.booking.presentation.events.home.viewmodel

import com.umnvd.booking.core.ui.viewmodel.BaseViewModel
import com.umnvd.booking.domain.events.usecases.GetMeetingEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MeetingEventsHomeViewModel @Inject constructor(
    getMeetingEventsUseCase: GetMeetingEventsUseCase,
) : BaseViewModel<MeetingEventsHomeScreenState>(MeetingEventsHomeScreenState()) {

}