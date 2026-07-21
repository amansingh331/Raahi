package com.example.raahi.presentation.AuthPage

import com.example.raahi.component.Button.PrimaryButton
import com.example.raahi.component.DividerWithText.DividerWithText
import com.example.raahi.component.SocialButton.SocialButton
import com.example.raahi.component.TextField.AuthTextField
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import com.example.raahi.R
import com.example.raahi.dataOpp.AuthResult
import com.example.raahi.dataOpp.AuthService
import com.example.raahi.navigation.Screen
import com.example.raahi.ui.theme.*
import kotlinx.coroutines.launch

@Composable
@Preview(showSystemUi = true)
fun LoginScreen(navController: NavHostController = rememberNavController()) {

//fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

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
                        text = "Welcome Back",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = LoginHeadingText,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Login to continue exploring amazing places.",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(28.dp))
                    if (error.isNotEmpty()) {
                        Text(
                            text = error,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            color = ErrorTextCol,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    AuthTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            error = ""
                        },
                        label = "Email",
                        leadingIcon = Icons.Default.Email
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    AuthTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            error = ""
                        },
                        label = "Password",
                        leadingIcon = Icons.Default.Lock,
                        isPassword = true,
                        passwordVisible = passwordVisible,
                        onPasswordToggle = {
                            passwordVisible = !passwordVisible
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "Forgot Password?",
                            color = TextSecondary,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clickable(enabled = !loading) {
                                navController.navigate(Screen.ForgotPassword.route)
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    PrimaryButton(
                        text = if (loading) "Logging In..." else "Login",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        isLoading = loading,
                        onClick = {
                            if (loading) return@PrimaryButton
                            scope.launch {
                                loading = true
                                error=""
                                when(val result = AuthService.login(email, password)){
                                    is AuthResult.Success -> {
                                        loading = false
                                        navController.navigate(Screen.Home.route) {
                                            popUpTo(Screen.Login.route) {
                                                inclusive = true
                                            }
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

                    Spacer(modifier = Modifier.height(30.dp))
                    DividerWithText(
                        text = "Or Sign In With"
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        SocialButton(
                            painter = painterResource(R.drawable.google),
                            contentDescription = "Google",
                            onClick = {
                            }
                        )

                        SocialButton(
                            painter = painterResource(R.drawable.apple),
                            contentDescription = "Apple",
                            onClick = {
                            }
                        )
                        SocialButton(
                            painter = painterResource(R.drawable.twitter),
                            contentDescription = "Twitter",
                            onClick = {
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Don't have an account?",
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Sign Up",
                            color = TextSecondary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable(enabled = !loading) {
                                navController.navigate(Screen.SignUp.route)
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }

}