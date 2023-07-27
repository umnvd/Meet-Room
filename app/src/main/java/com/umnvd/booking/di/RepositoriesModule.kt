package com.umnvd.booking.di

import com.umnvd.booking.data.repositories.AuthRepositoryImpl
import com.umnvd.booking.data.repositories.MeetingEventsRepositoryImpl
import com.umnvd.booking.data.repositories.MeetingRoomsRepositoryImpl
import com.umnvd.booking.data.repositories.UsersRepositoryImpl
import com.umnvd.booking.domain.repositories.AuthRepository
import com.umnvd.booking.domain.repositories.MeetingEventsRepository
import com.umnvd.booking.domain.repositories.MeetingRoomsRepository
import com.umnvd.booking.domain.repositories.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoriesModule {

    @Binds
    fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    fun bindUsersRepository(
        usersRepositoryImpl: UsersRepositoryImpl
    ): UsersRepository

    @Binds
    fun bindMeetingRoomsRepository(
        meetingRoomsRepositoryImpl: MeetingRoomsRepositoryImpl
    ): MeetingRoomsRepository

    @Binds
    fun bindMeetingEventsRepository(
        meetingEventsRepositoryImpl: MeetingEventsRepositoryImpl
    ): MeetingEventsRepository
}