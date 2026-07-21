package com.example.raahi.presentation.SplashScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.raahi.navigation.Screen
import kotlinx.coroutines.delay
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import com.example.raahi.dataOpp.AuthService

@Composable

fun SplashScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(2000)
        val loggedIn = AuthService.isLoggedIn()
        if (loggedIn) {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Splash.route) {
                    inclusive = true
                }
            }
        } else {
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Splash.route) {
                    inclusive = true
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "Raahi",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )

    }

}