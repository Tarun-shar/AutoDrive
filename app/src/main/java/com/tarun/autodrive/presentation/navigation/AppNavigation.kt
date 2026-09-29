package com.tarun.autodrive.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tarun.autodrive.presentation.screens.ForgotPasswordScreen
import com.tarun.autodrive.presentation.screens.LoginScreen
import com.tarun.autodrive.presentation.screens.SignUpScreen
import com.tarun.autodrive.presentation.screens.SplashScreen
import kotlinx.coroutines.delay

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            LaunchedEffect(Unit) {
                delay(2000)
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            }
            SplashScreen()
        }
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    // Handle login success navigation here
                },
                onSignUpClick = {
                    navController.navigate("signup")
                },
                onForgotPasswordClick = {
                    navController.navigate("forgot_password")
                }
            )
        }
        composable("signup") {
            SignUpScreen(
                onSignUpSuccess = {
                    // Handle sign up success navigation here
                },
                onLoginClick = {
                    navController.popBackStack()
                }
            )
        }
        composable("forgot_password") {
            ForgotPasswordScreen(
                onSendResetLinkClick = { email ->
                    // Handle send reset link here
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
