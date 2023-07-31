package com.umnvd.booking.domain.users.usecases

import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.domain.AppException
import com.umnvd.booking.domain.users.models.UserModel
import com.umnvd.booking.domain.users.repositories.UsersRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    suspend operator fun invoke(): Result<UserModel, AppException> {
        return try {
            val user = usersRepository.currentUser()
            return Result.Success(user)
        } catch (e: AppException) {
            Result.Error(e)
        }
    }
}