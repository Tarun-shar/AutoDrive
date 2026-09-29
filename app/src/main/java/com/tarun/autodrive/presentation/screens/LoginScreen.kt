package com.tarun.autodrive.presentation.screens

import android.util.Patterns
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarun.autodrive.R
import com.tarun.autodrive.presentation.Utils.AppOutlinedTextField
import com.tarun.autodrive.presentation.Utils.RequiredLabel
import com.tarun.autodrive.presentation.Utils.SocialLoginSection
import com.tarun.autodrive.presentation.Utils.SpacerShow
import com.tarun.autodrive.ui.theme.AutoDriveTheme

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    
    var passwordVisible by remember { mutableStateOf(false) }

    val errorEmailEmpty = stringResource(R.string.error_email_empty)
    val errorEmailInvalid = stringResource(R.string.error_email_invalid)
    val errorPasswordEmpty = stringResource(R.string.error_password_empty)
    val errorPasswordLength = stringResource(R.string.error_password_length)

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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(R.string.welcome_back),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = dimensionResource(R.dimen.text_size_headline).value.sp
                ),
                color = MaterialTheme.colorScheme.onBackground
            )

            SpacerShow(R.dimen.spacing_micro)

            Text(
                text = stringResource(R.string.login_subtitle),
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

            SpacerShow(R.dimen.spacing_medium)

            // Password Field
            RequiredLabel(textResId = R.string.password_label)


            SpacerShow(R.dimen.spacing_small)
            AppOutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = null
                },
                placeholderText = stringResource(R.string.password_hint),
                isError = passwordError != null,
                errorMessage = passwordError,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    TextButton(onClick = { passwordVisible = !passwordVisible }) {
                        Text(
                            text = if (passwordVisible) stringResource(R.string.action_hide) else stringResource(R.string.action_show),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            )

            SpacerShow(R.dimen.spacing_small)

            // Forgot Password
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = stringResource(R.string.forgot_password),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    modifier = Modifier.clickable { onForgotPasswordClick() }
                )
            }

            SpacerShow(R.dimen.spacing_large)

            // Login Button
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

                    if (password.isBlank()) {
                        passwordError = errorPasswordEmpty
                        isValid = false
                    } else if (password.length < 6) {
                        passwordError = errorPasswordLength
                        isValid = false
                    }

                    if (isValid) {
                        onLoginSuccess()
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
                    text = stringResource(R.string.login_button),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            SpacerShow(R.dimen.spacing_large)

            // Or continue with
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.or_continue_with),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            SpacerShow(R.dimen.spacing_medium)

            // Social Buttons (Google / Apple)
            SocialLoginSection()

            SpacerShow(R.dimen.spacing_extra_large)

            // Don't have an account? Sign Up
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.dont_have_account),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.sign_up_action),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onSignUpClick() }
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    AutoDriveTheme {
        LoginScreen()
    }
}
