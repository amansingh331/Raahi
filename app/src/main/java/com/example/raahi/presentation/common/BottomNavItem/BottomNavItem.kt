package com.example.raahi.presentation.common.BottomNavItem


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem(
        title = "Home",
        icon = Icons.Default.Home
    )

    object Explore : BottomNavItem(
        title = "Explore",
        icon = Icons.Default.Search
    )

    object Add : BottomNavItem(
        title = "Post",
        icon = Icons.Default.AddCircle
    )

    object Saved : BottomNavItem(
        title = "Saved",
        icon = Icons.Default.Favorite
    )

    object Profile : BottomNavItem(
        title = "Profile",
        icon = Icons.Default.Person
    )
}