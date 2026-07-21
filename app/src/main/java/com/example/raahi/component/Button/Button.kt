package com.example.raahi.component.Button

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.raahi.ui.theme.ContainerColor
import com.example.raahi.ui.theme.ContentColor
import com.example.raahi.ui.theme.DisabledContainerColor
import com.example.raahi.ui.theme.DisabledContentColor
import com.example.raahi.ui.theme.ProgressIndicatorColor

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {

    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled && !isLoading,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ContainerColor,
            contentColor = ContentColor,
            disabledContainerColor = DisabledContainerColor,
            disabledContentColor = DisabledContentColor
        )
    ) {

        if (isLoading) {
            CircularProgressIndicator(
                strokeWidth = 2.dp,
                color = ProgressIndicatorColor
            )
        } else {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )

        }

    }

}