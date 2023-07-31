package com.umnvd.booking.domain.users.repositories

import com.umnvd.booking.domain.users.models.UserModel

interface UsersRepository {
    suspend fun currentUser(): UserModel
    suspend fun user(uid: String): UserModel
    suspend fun allUsers(): List<UserModel>
}