package com.umnvd.booking.data.repositories

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.umnvd.booking.data.services.UsersService
import com.umnvd.booking.domain.models.User
import com.umnvd.booking.domain.repositories.UsersRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersService: UsersService,
) : UsersRepository {

    override suspend fun currentUser(): User {
        val userDto = usersService.getUser()
        val db = Firebase.firestore
        val user = db.collection("users").document(userUid).get().await()
    }

    override suspend fun allUsers(): List<User> {
        TODO("Not yet implemented")
    }
}