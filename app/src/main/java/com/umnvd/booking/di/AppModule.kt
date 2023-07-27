package com.umnvd.booking.di

import com.umnvd.booking.core.data.AppDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        ActivityModule::class,
        AssistedInjectModule::class,
        RepositoriesModule::class,
        FirebaseModule::class,
    ]
)
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideDispatchers(): AppDispatchers = AppDispatchers.Default
}