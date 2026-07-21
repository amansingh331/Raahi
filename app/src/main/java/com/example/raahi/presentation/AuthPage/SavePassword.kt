package com.example.raahi.presentation.AuthPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.raahi.component.Button.PrimaryButton
import com.example.raahi.component.TextField.AuthTextField
import com.example.raahi.navigation.Screen
import com.example.raahi.ui.theme.BackgroundBottom
import com.example.raahi.ui.theme.BackgroundTop
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.raahi.R
import com.example.raahi.dataOpp.AuthResult
import com.example.raahi.dataOpp.AuthService
import com.example.raahi.ui.theme.Backgroundcard
import com.example.raahi.ui.theme.ErrorTextCol
import com.example.raahi.ui.theme.LoginHeadingText
import com.example.raahi.ui.theme.TextPrimary

@Composable
//@Preview(showSystemUi = true)
fun SavePasswordScreen(navController: NavHostController) {

    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                BackgroundTop
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .size(90.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(30.dp))
            Card(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(
                    topStart = 35.dp,
                    topEnd = 35.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Backgroundcard
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                ) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Create New Password",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = LoginHeadingText,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Your new password must be different from your previous password.",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(28.dp))
                    if (error.isNotEmpty()) {
                        Text(
                            text = error,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            color = ErrorTextCol,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    AuthTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            error = ""
                        },
                        label = "New Password",
                        leadingIcon = Icons.Default.Lock,
                        isPassword = true,
                        passwordVisible = passwordVisible,
                        onPasswordToggle = {
                            passwordVisible = !passwordVisible
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    AuthTextField(
                        value = confirmPassword,
                        onValueChange = {
                            confirmPassword = it
                            error = ""
                        },
                        label = "Confirm Password",
                        leadingIcon = Icons.Default.Lock,
                        isPassword = true,
                        passwordVisible = confirmPasswordVisible,
                        onPasswordToggle = {
                            confirmPasswordVisible = !confirmPasswordVisible
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    PrimaryButton(
                        text = "Save Password",
                        isLoading = loading,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            if (loading) return@PrimaryButton
                            focusManager.clearFocus()
                            error = ""
                            if (password.isBlank()) {
                                error = "Please enter a password"
                                return@PrimaryButton
                            }
                            if (confirmPassword.isBlank()) {
                                error = "Please confirm your password"
                                return@PrimaryButton
                            }
                            if (password.length < 6) {
                                error = "Password must be at least 6 characters"
                                return@PrimaryButton
                            }
                            if (password != confirmPassword) {
                                error = "Passwords do not match"
                                return@PrimaryButton
                            }
                            scope.launch {
                                loading = true
                                error = ""
                                when (val result = AuthService.savePassword(password)) {
                                    is AuthResult.Success -> {
                                        loading = false
                                        navController.navigate(Screen.Home.route) {
                                            popUpTo(0) {
                                                inclusive = true
                                            }
                                            launchSingleTop = true
                                        }
                                    }

                                    is AuthResult.Error -> {
                                        loading = false
                                        error = result.message

                                    }
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    TextButton(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        enabled = !loading,
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Text("Back")
                    }
                }
            }
        }
    }
}
