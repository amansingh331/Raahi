package com.example.raahi.presentation.common.HomeBottomBar


import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.raahi.presentation.common.BottomNavItem.BottomNavItem

@Composable
fun HomeBottomBar(
    selectedItem: BottomNavItem,
    onItemSelected: (BottomNavItem) -> Unit
) {

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Explore,
        BottomNavItem.Add,
        BottomNavItem.Saved,
        BottomNavItem.Profile
    )

    NavigationBar {

        items.forEach { item ->

            NavigationBarItem(
                selected = selectedItem == item,
                onClick = {
                    onItemSelected(item)
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(item.title)
                }
            )

        }

    }

}