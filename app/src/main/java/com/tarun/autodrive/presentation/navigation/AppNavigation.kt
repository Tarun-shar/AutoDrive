package com.tarun.autodrive.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tarun.autodrive.presentation.screens.AddEditVehicleScreen
import com.tarun.autodrive.presentation.screens.AllRemindersScreen
import com.tarun.autodrive.presentation.screens.DashboardScreen
import com.tarun.autodrive.presentation.screens.EmailSentScreen
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
        startDestination = "dashboard"
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
                    navController.navigate("dashboard") {
                        popUpTo("login") { inclusive = true }
                    }
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
                    navController.navigate("dashboard") {
                        popUpTo("signup") { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.popBackStack()
                }
            )
        }
        composable("forgot_password") {
            ForgotPasswordScreen(
                onSendResetLinkClick = { email ->
                    navController.navigate("email_sent/$email")
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable("email_sent/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            EmailSentScreen(
                email = email,
                onBackClick = {
                    navController.popBackStack()
                },
                onBackToLoginClick = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("dashboard") {
            DashboardScreen(
                onAddClick = {
                    navController.navigate("add_edit_vehicle")
                },
                onLogoutClick = {
                    navController.navigate("login") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                },
                onSeeAllRemindersClick = {
                    navController.navigate("all_reminders")
                }
            )
        }
        composable("all_reminders") {
            AllRemindersScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable("add_edit_vehicle") {
            AddEditVehicleScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onSaveSuccess = {
                    navController.popBackStack()
                }
            )
        }
    }
}
