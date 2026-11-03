package com.example.raahi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.raahi.presentation.Auth.ForgotPasswordScreen
import com.example.raahi.presentation.Auth.LoginScreen
import com.example.raahi.presentation.Auth.OtpScreen
import com.example.raahi.presentation.Auth.SavePasswordScreen
import com.example.raahi.presentation.Auth.SignUpScreen
import com.example.raahi.presentation.Home.HomeScreen
import com.example.raahi.presentation.Splash.SplashScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }

        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(Screen.SignUp.route) {
            SignUpScreen(navController)
        }

        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(navController)
        }

        composable(Screen.OTP.route) {
            OtpScreen(navController)
        }
        composable(Screen.SavePassword.route) {
            SavePasswordScreen(navController)
        }

        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
    }
}