package com.umnvd.booking.data.auth.services

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.umnvd.booking.data.utils.withFBExceptionMapper
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthService @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) {
    suspend fun signIn(email: String, password: String): String = withFBExceptionMapper {
        val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        result.user!!.uid
    }

    suspend fun signOut() {
        return suspendCoroutine { continuation ->
            val authStateListener = object : AuthStateListener {
                override fun onAuthStateChanged(state: FirebaseAuth) {
                    if (state.currentUser == null) {
                        firebaseAuth.removeAuthStateListener(this)
                        continuation.resume(Unit)
                    }
                }

            }
            firebaseAuth.addAuthStateListener(authStateListener)
            firebaseAuth.signOut()
        }
    }

    fun currentUserUid(): String? = firebaseAuth.currentUser?.uid
}