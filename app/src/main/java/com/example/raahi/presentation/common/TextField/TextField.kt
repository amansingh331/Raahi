package com.example.raahi.presentation.common.TextField

import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.IconButton
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.raahi.ui.theme.CursorColor
import com.example.raahi.ui.theme.FocusedBorderColor
import com.example.raahi.ui.theme.FocusedLabelColor
import com.example.raahi.ui.theme.TextFieldelableColor
import com.example.raahi.ui.theme.UnFocusedBorderColor

@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector?=null,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onPasswordToggle: () -> Unit = {}
) {

    OutlinedTextField(
        value = value,

        onValueChange = onValueChange,

        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),

        label = {
            Text(text = label, color=TextFieldelableColor)
        },

        leadingIcon = {
            leadingIcon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = FocusedBorderColor
                )
            }
        },

        trailingIcon = {

            if (isPassword) {

                IconButton(
                    onClick = onPasswordToggle
                ) {

                    Icon(
                        imageVector =
                        if (passwordVisible)
                            Icons.Default.Visibility
                        else
                            Icons.Default.VisibilityOff,

                        contentDescription = null,
                        tint = FocusedBorderColor
                    )

                }

            }

        },

        visualTransformation =
        if (isPassword && !passwordVisible)
            PasswordVisualTransformation()
        else
            VisualTransformation.None,

        keyboardOptions = KeyboardOptions(
            keyboardType =
            if (isPassword)
                KeyboardType.Password
            else
                KeyboardType.Email
        ),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(18.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = FocusedBorderColor,
            unfocusedBorderColor = UnFocusedBorderColor,
            focusedLabelColor = FocusedLabelColor,
            cursorColor = CursorColor
        )
    )
}