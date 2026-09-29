package com.tarun.autodrive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.tarun.autodrive.presentation.navigation.AppNavigation
import com.tarun.autodrive.ui.theme.AutoDriveTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AutoDriveTheme {
                AppNavigation()
            }
        }
    }
}
