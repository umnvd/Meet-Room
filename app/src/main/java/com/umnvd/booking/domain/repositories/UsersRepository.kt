package com.umnvd.booking.domain.repositories

import com.umnvd.booking.domain.models.User

interface UsersRepository {

    suspend fun currentUser(): User

    suspend fun user(uid: String): User

    suspend fun allUsers(): List<User>
}