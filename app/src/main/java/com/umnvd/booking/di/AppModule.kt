package com.umnvd.booking.di

import com.umnvd.booking.core.data.AppDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {
    @Provides
    fun provideIoDispatcher(): AppDispatchers = AppDispatchers.Default
}