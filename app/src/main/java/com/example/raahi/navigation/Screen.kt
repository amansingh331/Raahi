package com.example.raahi.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object ForgotPassword : Screen("forgotpassword")
    object OTP : Screen("otp")
    object SavePassword : Screen("savepassword")
    object Home : Screen("home")
}