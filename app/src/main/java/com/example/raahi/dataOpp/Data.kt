package com.example.raahi.dataOpp

import kotlinx.coroutines.delay

data class User(
    val fullName: String,
    val email: String,
    var password: String,
    var verified: Boolean = false
)

object AuthService {
    private val users = mutableListOf<User>()
    private var loggedInUser: User? = null
    private var pendingVerificationEmail: String? = null

    suspend fun login(email: String, password: String): AuthResult {
        delay(3000)
        val user = users.find { it.email == email } ?: return AuthResult.Error("User not found")
        if (!user.verified) {
            return AuthResult.Error("Please verify your account")
        }
        if (user.password != password) {
            return AuthResult.Error("Incorrect password")
        }
        loggedInUser = user
        return AuthResult.Success
    }

    suspend fun signUp(fullName: String, email: String): AuthResult {
        delay(3000)
        if (users.any { it.email == email }) {
            return AuthResult.Error("Email already exists")
        }
        users.add(
            User(
                fullName = fullName,
                email = email,
                password = "",
                verified = false
            )
        )
        pendingVerificationEmail = email
        return AuthResult.Success
    }

    suspend fun forgotPassword(email: String): AuthResult {
        delay(3000)
        val user = users.find { it.email == email } ?: return AuthResult.Error("Email not found")
        pendingVerificationEmail = user.email
        return AuthResult.Success
    }

    suspend fun verifyOtp(otp: String): AuthResult {
        delay(3000)
        if (otp != "123456") {
            return AuthResult.Error("Invalid OTP")
        }
        return AuthResult.Success
    }

    suspend fun resendOtp(): AuthResult {
        delay(3000)
        val email = pendingVerificationEmail
        if (email == null) {
            return AuthResult.Error("No pending verification found.")
        }
        return AuthResult.Success
    }

    suspend fun savePassword(password: String): AuthResult {
        delay(3000)
        val email = pendingVerificationEmail ?: return AuthResult.Error("No verification in progress")
        val user = users.find { it.email == email } ?: return AuthResult.Error("User not found")
        user.password = password
        user.verified = true
        pendingVerificationEmail = null
        return AuthResult.Success
    }

    fun isLoggedIn(): Boolean {
        return loggedInUser != null
    }

    fun logout() {
        loggedInUser = null
    }

    fun getCurrentUser(): User? {
        return loggedInUser
    }
}