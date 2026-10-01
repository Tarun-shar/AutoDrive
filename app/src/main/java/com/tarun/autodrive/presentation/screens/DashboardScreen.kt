package com.tarun.autodrive.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.tarun.autodrive.R
import com.tarun.autodrive.presentation.Utils.SpacerShow
import com.tarun.autodrive.presentation.screens.MainScreens.HomeScreen
import com.tarun.autodrive.ui.theme.AutoDriveTheme

sealed class BottomNavItem(val route: String, val titleResId: Int, val iconResId: Int) {
    object Home : BottomNavItem("home", R.string.nav_home, R.drawable.home)
    object Vehicles : BottomNavItem("vehicles", R.string.nav_vehicles, R.drawable.vehicle)
    object Add : BottomNavItem("add", R.string.app_name, R.drawable.add)
    object Trips : BottomNavItem("trips", R.string.nav_trips, R.drawable.trips)
    object Profile : BottomNavItem("profile", R.string.nav_profile, R.drawable.profile)
}

@Composable
fun DashboardScreen(
    onAddClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    onSeeAllRemindersClick: () -> Unit = {},
) {
    var selectedTab by remember { mutableStateOf("home") }

    Scaffold(
        bottomBar = {
            CustomBottomBar(
                selectedRoute = selectedTab,
            ) { route ->
                if (route == "add") {
                    onAddClick()
                } else {
                    selectedTab = route
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when (selectedTab) {
                "home" -> HomeScreen(
                    onSeeAllClick = onSeeAllRemindersClick,
                    onNotificationClick = onSeeAllRemindersClick
                )
                "vehicles" -> VehiclesScreenContent()
                "trips" -> TripsScreenContent()
                "profile" -> ProfileScreenContent(onLogoutClick = onLogoutClick)
            }
        }
    }
}

@Composable
fun CustomBottomBar(
    selectedRoute: String,
    onTabSelected: (String) -> Unit
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Vehicles,
        BottomNavItem.Add,
        BottomNavItem.Trips,
        BottomNavItem.Profile
    )

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RectangleShape,
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = dimensionResource(R.dimen.card_elevation)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            HorizontalDivider(
                thickness = dimensionResource(R.dimen.divider_thickness),
                color = MaterialTheme.colorScheme.outlineVariant
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.bottom_bar_height))
                    .padding(
                        horizontal = dimensionResource(R.dimen.spacing_small),
                        vertical = dimensionResource(R.dimen.spacing_micro)
                    ),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEach { item ->
                    if (item is BottomNavItem.Add) {
                        // Center Floating Action Button style
                        Box(
                            modifier = Modifier
                                .size(dimensionResource(R.dimen.fab_size))
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary)
                                .clickable { onTabSelected(item.route) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = item.iconResId),
                                contentDescription = stringResource(R.string.app_name),
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(dimensionResource(R.dimen.icon_size_fab))
                            )
                        }
                    } else {
                        val isSelected = selectedRoute == item.route
                        val tintColor = if (isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                        val labelColor = if (isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }

                        Column(
                            modifier = Modifier
                                .clip(RoundedCornerShape(dimensionResource(R.dimen.input_corner_radius)))
                                .clickable { onTabSelected(item.route) }
                                .padding(
                                    horizontal = dimensionResource(R.dimen.spacing_small),
                                    vertical = dimensionResource(R.dimen.spacing_micro)
                                ),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(id = item.iconResId),
                                contentDescription = stringResource(id = item.titleResId),
                                tint = tintColor,
                                modifier = Modifier.size(dimensionResource(R.dimen.icon_size_medium))
                            )
                            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_micro)))
                            Text(
                                text = stringResource(id = item.titleResId),
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = dimensionResource(R.dimen.text_size_caption).value.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                ),
                                color = labelColor
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun VehiclesScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.spacing_medium)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.my_vehicles_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TripsScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.spacing_medium)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.trip_history_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ProfileScreenContent(onLogoutClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.spacing_medium)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.profile_settings_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )
        SpacerShow(R.dimen.spacing_extra_large)
        Button(onClick = onLogoutClick) {
            Text(stringResource(R.string.menu_logout))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun DashboardScreenPreview() {
    AutoDriveTheme {
        DashboardScreen()
    }
}
