package com.example.raahi.presentation.Home

import com.example.raahi.presentation.common.BottomNavItem.BottomNavItem
import com.example.raahi.presentation.common.HomeBottomBar.HomeBottomBar
import com.example.raahi.presentation.common.HomeTopBar.HomeTopBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.raahi.dataOpp.AuthService
import com.example.raahi.navigation.Screen
import com.example.raahi.presentation.Profile.ProfileScreen
import com.example.raahi.presentation.Saved.SavedScreen
import com.example.raahi.presentation.Search.SearchScreen
import com.example.raahi.presentation.Upload.UploadScreen

@Composable
@Preview(showSystemUi = true)
fun HomeScreen(navController: NavHostController = rememberNavController()) {


//fun HomeScreen(navController: NavHostController) {

    var selectedTab by remember {
        mutableStateOf<BottomNavItem>(BottomNavItem.Home)
    }

    Scaffold(

        topBar = {

            HomeTopBar(

                onProfileClick = {

                    // TODO

                },

                onMessageClick = {

                    // TODO

                }

            )

        },
        bottomBar = {
            HomeBottomBar(
                selectedItem = selectedTab,
                onItemSelected = {
                    selectedTab = it
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when (selectedTab) {
                BottomNavItem.Home -> {
                    Text("Home")
                }
                BottomNavItem.Explore -> {
                    SearchScreen()
                }
                BottomNavItem.Add -> {
                    UploadScreen()
                }
                BottomNavItem.Saved -> {
                    SavedScreen()
                }
                BottomNavItem.Profile -> {
                    ProfileScreen(
                        onLogout = {
                            AuthService.logout()
                            navController.navigate(Screen.Login.route) {
                                popUpTo(0) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}