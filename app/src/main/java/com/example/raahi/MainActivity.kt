package com.example.raahi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.raahi.navigation.AppNavigation
import com.example.raahi.ui.theme.RaahiTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            RaahiTheme {
                AppNavigation()
            }
        }
    }
}