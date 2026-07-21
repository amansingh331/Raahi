package com.example.raahi.presentation.HomeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.raahi.dataOpp.AuthService
import com.example.raahi.navigation.Screen

@Composable
fun HomeScreen(
    navController: NavHostController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Raahi",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Welcome!",
            style = MaterialTheme.typography.bodyLarge
        )

        Button(
            modifier = Modifier.padding(top = 32.dp),
            onClick = {

                AuthService.logout()

                navController.navigate(Screen.Login.route) {
                    popUpTo(0) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        ) {
            Text("Logout")
        }
    }
}