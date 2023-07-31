package com.umnvd.booking.domain.users.usecases

import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.domain.AppException
import com.umnvd.booking.domain.users.models.UserModel
import com.umnvd.booking.domain.users.repositories.UsersRepository
import javax.inject.Inject

class GetUserListUseCase @Inject constructor(
    private val usersRepository: UsersRepository,
) {

    suspend operator fun invoke(): Result<List<UserModel>, AppException> {
        val users = usersRepository.allUsers()
        return Result.Success(users)
    }
}