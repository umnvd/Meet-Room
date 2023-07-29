package com.umnvd.booking.domain.users.usecases

import com.umnvd.booking.domain.users.models.UserModel
import com.umnvd.booking.domain.users.repositories.UsersRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    suspend operator fun invoke(): UserModel {
        return usersRepository.currentUser()
    }
}