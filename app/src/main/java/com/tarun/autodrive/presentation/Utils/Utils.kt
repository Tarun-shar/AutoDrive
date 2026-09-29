package com.tarun.autodrive.presentation.Utils

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.tarun.autodrive.R

@Composable
fun SpacerShow(
    modifier: Int
) {
    Spacer(
        modifier = Modifier.height(
            dimensionResource(id = modifier)
        )
    )
}

@Composable
fun RequiredLabel(
    textResId: Int,
    modifier: Modifier = Modifier
) {
    val text = stringResource(textResId)
    Text(
        text = buildAnnotatedString {
            if (text.endsWith("*")) {
                append(text.substringBeforeLast("*"))
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.error)) {
                    append("*")
                }
            } else {
                append(text)
            }
        },
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier
    )
}

@Composable
fun AppOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    modifier: Modifier = Modifier.fillMaxWidth(),
    isError: Boolean = false,
    errorMessage: String? = null,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = { Text(placeholderText) },
        isError = isError,
        singleLine = singleLine,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        trailingIcon = trailingIcon,
        shape = RoundedCornerShape(dimensionResource(R.dimen.input_corner_radius)),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline
        )
    )
    if (errorMessage != null) {
        SpacerShow(R.dimen.spacing_micro)
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun SocialLoginSection(
    onGoogleClick: () -> Unit = {},
    onAppleClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_medium))
    ) {
        OutlinedButton(
            onClick = onGoogleClick,
            modifier = Modifier
                .weight(1f)
                .height(dimensionResource(R.dimen.button_height)),
            shape = RoundedCornerShape(dimensionResource(R.dimen.button_corner_radius)),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.onBackground
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.google),
                contentDescription = "Google",
                modifier = Modifier.size(dimensionResource(R.dimen.icon_size_social)),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = stringResource(R.string.google_sign_in))
        }

        OutlinedButton(
            onClick = onAppleClick,
            modifier = Modifier
                .weight(1f)
                .height(dimensionResource(R.dimen.button_height)),
            shape = RoundedCornerShape(dimensionResource(R.dimen.button_corner_radius)),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.onBackground
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.apple),
                contentDescription = "Apple",
                modifier = Modifier.size(dimensionResource(R.dimen.icon_size_social)),
                tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = stringResource(R.string.apple_sign_in))
        }
    }
}
