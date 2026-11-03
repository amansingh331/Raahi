package com.example.raahi.presentation.Auth


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.raahi.R
import com.example.raahi.presentation.common.Button.PrimaryButton
import com.example.raahi.presentation.common.DividerWithText.DividerWithText
import com.example.raahi.presentation.common.SocialButton.SocialButton
import com.example.raahi.presentation.common.TextField.AuthTextField
import com.example.raahi.dataOpp.AuthResult
import com.example.raahi.dataOpp.AuthService
import com.example.raahi.navigation.Screen
import com.example.raahi.ui.theme.*
import kotlinx.coroutines.launch

@Composable
@Preview(showSystemUi = true)
fun SignUpScreen(navController: NavHostController = rememberNavController()) {

//
//fun SignUpScreen(navController: NavHostController) {

    val configuration = LocalConfiguration.current
    val cardMinHeight = configuration.screenHeightDp.dp - 180.dp
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }
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
                contentDescription = "Logo",
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
                    modifier = Modifier
                        .padding(24.dp)
                        .padding(bottom = 40.dp)
                ) {

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Create Account",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = SignUpHeadingText,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Join Raahi and start exploring amazing places.",
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
                            color = ErrorTextCol,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    AuthTextField(
                        value = fullName,
                        onValueChange = {
                            fullName = it
                            error = ""
                        },
                        label = "Full Name",
                        leadingIcon = Icons.Default.Person
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    AuthTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            error = ""
                        },
                        label = "Email",
                        leadingIcon = Icons.Default.Email
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    PrimaryButton(
                        text = "Create Account",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        isLoading = loading,

                        onClick = {
                            if (loading) return@PrimaryButton

                            if (fullName.isBlank()) {
                                error = "Please enter your full name"
                                return@PrimaryButton
                            }

                            if (email.isBlank()) {
                                error = "Please enter your email"
                                return@PrimaryButton
                            }
                            focusManager.clearFocus()
                            scope.launch {
                                loading = true
                                error = ""
                                when(
                                    val result = AuthService.signUp(
                                        fullName,
                                        email
                                    )
                                ){
                                    is AuthResult.Success -> {
                                        loading = false
                                        navController.navigate(Screen.OTP.route)
                                    }
                                    is AuthResult.Error -> {
                                        loading = false
                                        error = result.message
                                    }
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    DividerWithText(
                        text = "Or Sign Up With"
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        SocialButton(
                            painter = painterResource(R.drawable.google),
                            contentDescription = "Google",
                            onClick = {}
                        )
                        SocialButton(
                            painter = painterResource(R.drawable.apple),
                            contentDescription = "Apple",
                            onClick = {}
                        )
                        SocialButton(
                            painter = painterResource(R.drawable.twitter),
                            contentDescription = "Twitter",
                            onClick = {}
                        )
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Already have an account? ",
                            color = TextPrimary
                        )
                        Text(
                            text = "Login",
                            color = TextSecondary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable(enabled = !loading) {
                                navController.popBackStack()
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}