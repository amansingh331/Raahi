package com.example.raahi.presentation.common.HomeTopBar


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    onProfileClick: () -> Unit,
    onMessageClick: () -> Unit
) {

    CenterAlignedTopAppBar(

        title = {},

        navigationIcon = {

            IconButton(
                onClick = onProfileClick
            ) {

                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile"
                )

            }

        },

        actions = {

            IconButton(
                onClick = onMessageClick
            ) {

                Icon(
                    imageVector = Icons.Default.Message,
                    contentDescription = "Messages"
                )

            }

        }

    )

}