package com.example.raahi.presentation.Splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.raahi.R
import com.example.raahi.dataOpp.AuthService
import com.example.raahi.navigation.Screen
import com.example.raahi.ui.theme.SplashBackground
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SplashScreen(
    navController: NavController
) {

    val isPreview = LocalInspectionMode.current

    val screenHeight = LocalConfiguration.current.screenHeightDp

    // Starts near the bottom
    val startOffset = screenHeight * 0.45f

    // Ends well above the center (near the top)
    val endOffset = -(screenHeight * 0.75f)

    val offsetY = remember {
        Animatable(startOffset)
    }

    val scale = remember {
        Animatable(2.8f)
    }

    val logoAlpha = remember {
        Animatable(0f)
    }

    LaunchedEffect(Unit) {

        coroutineScope {

            // Fade in
            launch {
                logoAlpha.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(250)
                )
            }

            // Continuous movement Bottom -> Top
            launch {
                offsetY.animateTo(
                    targetValue = endOffset,
                    animationSpec = tween(
                        durationMillis = 2200,
                        easing = FastOutSlowInEasing
                    )
                )
            }

            // Shrink only during first half
            launch {
                scale.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = 1100,
                        easing = FastOutSlowInEasing
                    )
                )
            }
        }

        if (!isPreview) {

            if (AuthService.isLoggedIn()) {

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
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SplashBackground),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = null,
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = 0,
                        y = offsetY.value.roundToInt()
                    )
                }
                .graphicsLayer {
                    scaleX = scale.value
                    scaleY = scale.value
                    alpha = logoAlpha.value
                }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(
        navController = rememberNavController()
    )
}