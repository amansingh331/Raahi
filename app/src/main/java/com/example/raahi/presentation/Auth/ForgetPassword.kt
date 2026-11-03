package com.example.raahi.presentation.Auth


import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.raahi.R
import com.example.raahi.presentation.common.Button.PrimaryButton
import com.example.raahi.presentation.common.TextField.AuthTextField
import com.example.raahi.dataOpp.AuthResult
import com.example.raahi.dataOpp.AuthService
import com.example.raahi.navigation.Screen
import com.example.raahi.ui.theme.*
import kotlinx.coroutines.launch

@Composable
@Preview(showSystemUi = true)
fun ForgotPasswordScreen(navController: NavHostController = rememberNavController()) {


//fun ForgotPasswordScreen(navController: NavHostController) {
    val configuration = LocalConfiguration.current
    val cardMinHeight = configuration.screenHeightDp.dp - 180.dp
    var email by remember {
        mutableStateOf("")
    }
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
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = cardMinHeight),
                colors = CardDefaults.cardColors(
                    containerColor = Backgroundcard
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(
                    topStart = 35.dp,
                    topEnd = 35.dp
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Forgot Password",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = ForgetPasswordHeadingText,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Enter your email address and we'll send you an OTP.",
                        color = TextPrimary,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                    )
                    Spacer(modifier = Modifier.height(28.dp))
                    if (error.isNotEmpty()) {
                        Text(
                            text = error,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            color = Color.Red,
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
                    Spacer(modifier = Modifier.height(30.dp))
                    PrimaryButton(
                        text = "Send OTP",
                        isLoading = loading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        onClick = {
                            if (loading) return@PrimaryButton
                            if (email.isBlank()) {
                                error = "Please enter your email"
                                return@PrimaryButton
                            }

                            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                error = "Please enter a valid email address"
                                return@PrimaryButton
                            }
                            scope.launch {
                                loading = true
                                when(val result = AuthService.forgotPassword(email)){
                                    is AuthResult.Success ->{
                                        loading = false
                                        navController.navigate(Screen.OTP.route)

                                    }
                                    is AuthResult.Error ->{
                                        loading = false
                                        error = result.message
                                    }
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Remember your password? ",
                            color = TextPrimary
                        )
                        Text(
                            text = "Login",
                            color = TextSecondary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                navController.popBackStack()
                            }
                        )
                    }
                }

            }

        }

    }
}