package com.tarun.autodrive.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tarun.autodrive.presentation.screens.AddEditVehicleScreen
import com.tarun.autodrive.presentation.screens.AllRemindersScreen
import com.tarun.autodrive.presentation.screens.DashboardScreen
import com.tarun.autodrive.presentation.screens.EmailSentScreen
import com.tarun.autodrive.presentation.screens.ForgotPasswordScreen
import com.tarun.autodrive.presentation.screens.LoginScreen
import com.tarun.autodrive.presentation.screens.SignUpScreen
import com.tarun.autodrive.presentation.screens.SplashScreen
import com.tarun.autodrive.presentation.screens.VehicleDetailsScreen
import kotlinx.coroutines.delay

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "dashboard?tab=home"
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
                    navController.navigate("dashboard?tab=home") {
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
                    navController.navigate("dashboard?tab=home") {
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
        composable(
            route = "dashboard?tab={tab}",
            arguments = listOf(
                navArgument("tab") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = "home"
                }
            )
        ) { backStackEntry ->
            val tab = backStackEntry.arguments?.getString("tab") ?: "home"
            DashboardScreen(
                initialTab = tab,
                onAddClick = {
                    navController.navigate("add_edit_vehicle")
                },
                onLogoutClick = {
                    navController.navigate("login") {
                        popUpTo("dashboard?tab=home") { inclusive = true }
                    }
                },
                onSeeAllRemindersClick = {
                    navController.navigate("all_reminders")
                },
                onVehicleClick = { vehicleId ->
                    navController.navigate("vehicle_details/$vehicleId")
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
        composable("vehicle_details/{vehicleId}") { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString("vehicleId") ?: "creta"
            VehicleDetailsScreen(
                vehicleId = vehicleId,
                onBackClick = {
                    navController.navigate("dashboard?tab=vehicles") {
                        popUpTo("dashboard?tab=home") { inclusive = true }
                    }
                },
                onEditClick = { id ->
                    navController.navigate("add_edit_vehicle?vehicleId=$id")
                }
            )
        }
        composable(
            route = "add_edit_vehicle?vehicleId={vehicleId}",
            arguments = listOf(
                navArgument("vehicleId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString("vehicleId")
            AddEditVehicleScreen(
                vehicleId = vehicleId,
                onBackClick = {
                    navController.navigate("dashboard?tab=vehicles") {
                        popUpTo("dashboard?tab=home") { inclusive = true }
                    }
                },
                onSaveSuccess = {
                    navController.navigate("dashboard?tab=vehicles") {
                        popUpTo("dashboard?tab=home") { inclusive = true }
                    }
                }
            )
        }
    }
}
