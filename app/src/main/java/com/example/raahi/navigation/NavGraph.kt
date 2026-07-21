package com.example.raahi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.raahi.presentation.AuthPage.ForgotPasswordScreen
import com.example.raahi.presentation.AuthPage.LoginScreen
import com.example.raahi.presentation.AuthPage.OtpScreen
import com.example.raahi.presentation.AuthPage.SavePasswordScreen
import com.example.raahi.presentation.AuthPage.SignUpScreen
import com.example.raahi.presentation.HomeScreen.HomeScreen
import com.example.raahi.presentation.SplashScreen.SplashScreen

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