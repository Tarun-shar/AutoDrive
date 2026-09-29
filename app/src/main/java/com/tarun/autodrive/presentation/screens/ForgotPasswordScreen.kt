package com.tarun.autodrive.presentation.screens

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarun.autodrive.R
import com.tarun.autodrive.presentation.Utils.AppOutlinedTextField
import com.tarun.autodrive.presentation.Utils.RequiredLabel
import com.tarun.autodrive.presentation.Utils.SpacerShow
import com.tarun.autodrive.ui.theme.AutoDriveTheme

@Composable
fun ForgotPasswordScreen(
    onSendResetLinkClick: (String) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }

    val errorEmailEmpty = stringResource(R.string.error_email_empty)
    val errorEmailInvalid = stringResource(R.string.error_email_invalid)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = dimensionResource(R.dimen.screen_padding_horizontal),
                    vertical = dimensionResource(R.dimen.screen_padding_vertical)
                ),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            // Back button with negative horizontal offset to align flush with screen edge padding
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.offset(x = (-12).dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.back_arrow),
                    contentDescription = stringResource(R.string.legend_back),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            SpacerShow(R.dimen.spacing_medium)

            Image(
                painter = painterResource(R.drawable.mail_logo),
                contentDescription = "mail_logo",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            SpacerShow(R.dimen.spacing_medium)

            Text(
                text = stringResource(R.string.forgot_password_title),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = dimensionResource(R.dimen.text_size_headline).value.sp
                ),
                color = MaterialTheme.colorScheme.onBackground
            )

            SpacerShow(R.dimen.spacing_micro)

            Text(
                text = stringResource(R.string.forgot_password_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            SpacerShow(R.dimen.spacing_large)

            // Email Field
            RequiredLabel(textResId = R.string.email_label)
            SpacerShow(R.dimen.spacing_small)
            AppOutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = null
                },
                placeholderText = stringResource(R.string.email_hint),
                isError = emailError != null,
                errorMessage = emailError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            SpacerShow(R.dimen.spacing_large)

            // Send Reset Link Button
            Button(
                onClick = {
                    var isValid = true
                    if (email.isBlank()) {
                        emailError = errorEmailEmpty
                        isValid = false
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        emailError = errorEmailInvalid
                        isValid = false
                    }

                    if (isValid) {
                        onSendResetLinkClick(email)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.button_height)),
                shape = RoundedCornerShape(dimensionResource(R.dimen.button_corner_radius)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = stringResource(R.string.send_reset_link),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ForgotPasswordScreenPreview() {
    AutoDriveTheme {
        ForgotPasswordScreen()
    }
}
