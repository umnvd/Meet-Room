package com.umnvd.booking.data.services

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.umnvd.booking.domain.errors.EmailNotRegisteredException
import com.umnvd.booking.domain.errors.NetworkException
import com.umnvd.booking.domain.errors.PasswordInvalidException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthService @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) {
    suspend fun signIn(email: String, password: String): String {
        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            return result.user!!.uid
        } catch (e: FirebaseNetworkException) {
            throw NetworkException()
        } catch (e: FirebaseAuthInvalidUserException) {
            throw EmailNotRegisteredException()
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            throw PasswordInvalidException()
        }
    }

    suspend fun signOut() {
        return suspendCoroutine { continuation ->
            val authStateListener = object : AuthStateListener {
                override fun onAuthStateChanged(state: FirebaseAuth) {
                    if (state.currentUser == null) {
                        continuation.resume(Unit)
                        firebaseAuth.removeAuthStateListener(this)
                    }
                }

            }
            firebaseAuth.addAuthStateListener(authStateListener)
            firebaseAuth.signOut()
        }
    }
}