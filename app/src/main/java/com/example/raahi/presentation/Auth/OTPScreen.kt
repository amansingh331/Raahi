package com.example.raahi.presentation.Auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.raahi.R
import com.example.raahi.presentation.common.Button.PrimaryButton
import com.example.raahi.dataOpp.AuthResult
import com.example.raahi.dataOpp.AuthService
import com.example.raahi.navigation.Screen
import com.example.raahi.ui.theme.*
import kotlinx.coroutines.launch

@Composable
@Preview(showSystemUi = true)
fun OtpScreen(navController: NavHostController= rememberNavController()) {

    val configuration = LocalConfiguration.current
    val cardMinHeight = configuration.screenHeightDp.dp - 180.dp
    var otp by remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()

    var loading by remember {
        mutableStateOf(false)
    }

    var error by remember {
        mutableStateOf("")
    }
    var message by remember { mutableStateOf("") }
    var canResend by remember { mutableStateOf(false) }
    var secondsLeft by remember { mutableStateOf(30) }
    val focusManager = LocalFocusManager.current
    LaunchedEffect(canResend) {

        if (!canResend) {

            secondsLeft = 30

            while (secondsLeft > 0) {
                kotlinx.coroutines.delay(1000)
                secondsLeft--
            }

            canResend = true
        }
    }

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
                shape = RoundedCornerShape(
                    topStart = 35.dp,
                    topEnd = 35.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Backgroundcard
                )
            ) {

                Column(
                    modifier = Modifier.padding(24.dp)
                ) {

                    Text(
                        text = "Verify OTP",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = OTPHeadingText,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Enter the 6-digit code sent to your email.",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = TextPrimary
                    )

                    Spacer(modifier = Modifier.height(30.dp))
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
                    if (message.isNotEmpty()) {
                        Text(
                            text = message,
                            color = Color(0xFF2E7D32), // Green
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    OutlinedTextField(
                        value = otp,
                        onValueChange = {
                            if (it.length <= 6) {
                                otp = it
                                error = ""
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text("OTP")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        shape = RoundedCornerShape(18.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = FocusedBorderColor,
                            unfocusedBorderColor = UnFocusedBorderColor,
                            cursorColor = CursorColor
                        )
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    PrimaryButton(
                        text = "Verify OTP",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        isLoading = loading,
                        onClick = {
                            if (loading) return@PrimaryButton
                            focusManager.clearFocus()
                            if (otp.isBlank()) {
                                error = "Please enter the OTP"
                                return@PrimaryButton
                            }
                            if (otp.length != 6) {
                                error = "OTP must be 6 digits"
                                return@PrimaryButton
                            }
                            scope.launch {
                                loading = true
                                error = ""
                                message = ""
                                when (val result = AuthService.verifyOtp(otp)) {
                                    is AuthResult.Success -> {
                                        loading = false
                                        navController.navigate(Screen.SavePassword.route) {
                                            popUpTo(Screen.OTP.route) {
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
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Didn't receive the code? ",
                            color = TextPrimary
                        )
                        Text(
                            text = if (canResend) "Resend" else "Resend in ${secondsLeft}s",
                            color = if (canResend) Primary else TextSecondary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable(enabled = canResend && !loading) {
                                focusManager.clearFocus()
                                scope.launch {
                                    loading = true
                                    error = ""
                                    message = ""
                                    when (val result = AuthService.resendOtp()) {
                                        is AuthResult.Success -> {
                                            loading = false
                                            message = "OTP sent successfully."
                                            secondsLeft = 30
                                            canResend = false
                                        }
                                        is AuthResult.Error -> {
                                            loading = false
                                            error = result.message
                                        }
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}