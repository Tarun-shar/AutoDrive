package com.tarun.autodrive.presentation.screens.MainScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarun.autodrive.R
import com.tarun.autodrive.presentation.model.ReminderItemModel
import com.tarun.autodrive.presentation.model.ReminderRepository
import com.tarun.autodrive.presentation.Utils.SpacerShow
import com.tarun.autodrive.presentation.screens.ReminderCardItem
import com.tarun.autodrive.ui.theme.AutoDriveTheme

@Composable
fun HomeScreen(
    onAddVehicleClick: () -> Unit = {},
    onTripHistoryClick: () -> Unit = {},
    onMaintenanceClick: () -> Unit = {},
    onLocationClick: () -> Unit = {},
    onChangeVehicleClick: () -> Unit = {},
    onSeeAllClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = dimensionResource(R.dimen.screen_padding_horizontal),
                    vertical = dimensionResource(R.dimen.screen_padding_vertical)
                ),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            // Header Row (Greeting & Notification)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.greeting_format),
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = dimensionResource(R.dimen.text_size_headline).value.sp
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = stringResource(R.string.dashboard_subtitle),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                IconButton(onClick = onNotificationClick) {
                    Icon(
                        painter = painterResource(R.drawable.notification),
                        contentDescription = "Notification",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(dimensionResource(R.dimen.icon_size_medium))
                    )
                }
            }

            SpacerShow(R.dimen.spacing_large)

            // My Vehicle Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius)),
                color = MaterialTheme.colorScheme.surfaceVariant,
                tonalElevation = dimensionResource(R.dimen.card_elevation)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.spacing_medium))
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.my_vehicle_header),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = stringResource(R.string.change_vehicle),
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.clickable { onChangeVehicleClick() }
                        )
                    }

                    SpacerShow(R.dimen.spacing_small)

                    // Vehicle Info & Image Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = stringResource(R.string.vehicle_hyundai_creta),
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = stringResource(R.string.vehicle_hyundai_creta_no),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Image(
                            painter = painterResource(R.drawable.creta),
                            contentDescription = "Hyundai Creta",
                            modifier = Modifier
                                .size(width = 150.dp, height = 90.dp)
                        )
                    }

                    SpacerShow(R.dimen.spacing_small)

                    // Metric Sub-cards (Fuel Level & Odometer)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small))
                    ) {
                        val fuelColor = colorResource(R.color.accent_green)
                        val odometerColor = colorResource(R.color.accent_blue)

                        // Fuel Level Card
                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surface
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(dimensionResource(R.dimen.spacing_small)),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(fuelColor.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.vehicle),
                                        contentDescription = "Fuel",
                                        tint = fuelColor,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Column {
                                    Text(
                                        text = stringResource(R.string.fuel_level_label),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = "70%",
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                            }
                        }

                        // Odometer Card
                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surface
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(dimensionResource(R.dimen.spacing_small)),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(odometerColor.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.trips),
                                        contentDescription = "Odometer",
                                        tint = odometerColor,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Column {
                                    Text(
                                        text = stringResource(R.string.odometer_label),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = "12,456 km",
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                            }
                        }
                    }
                }
            }

            SpacerShow(R.dimen.spacing_large)

            // Quick Actions Header
            Text(
                text = stringResource(R.string.quick_actions_header),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )

            SpacerShow(R.dimen.spacing_small)

            // Quick Actions 2x2 Grid
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small))
                ) {
                    QuickActionCard(
                        icon = R.drawable.vehicle,
                        label = stringResource(R.string.action_add_vehicle),
                        tintColor = colorResource(R.color.accent_blue),
                        modifier = Modifier.weight(1f),
                        onClick = onAddVehicleClick
                    )
                    QuickActionCard(
                        icon = R.drawable.trips,
                        label = stringResource(R.string.action_trip_history),
                        tintColor = colorResource(R.color.accent_green),
                        modifier = Modifier.weight(1f),
                        onClick = onTripHistoryClick
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small))
                ) {
                    QuickActionCard(
                        icon = R.drawable.home,
                        label = stringResource(R.string.action_maintenance),
                        tintColor = colorResource(R.color.accent_purple),
                        modifier = Modifier.weight(1f),
                        onClick = onMaintenanceClick
                    )
                    QuickActionCard(
                        icon = R.drawable.profile,
                        label = stringResource(R.string.action_location),
                        tintColor = colorResource(R.color.accent_teal),
                        modifier = Modifier.weight(1f),
                        onClick = onLocationClick
                    )
                }
            }

            SpacerShow(R.dimen.spacing_large)

            // Upcoming Service Header with "See All"
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.upcoming_service_header),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = stringResource(R.string.see_all),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onSeeAllClick() }
                )
            }

            SpacerShow(R.dimen.spacing_small)

            // Upcoming Service Cards (Showing max 3 items)
            val reminders = ReminderRepository.getReminders().take(3)
            if (reminders.isEmpty()) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius)),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    tonalElevation = dimensionResource(R.dimen.card_elevation)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(R.dimen.spacing_medium)),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.empty_reminders_title),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = stringResource(R.string.empty_reminders_subtitle),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small))
                ) {
                    reminders.forEach { reminder ->
                        ReminderCardItem(reminder = reminder)
                    }
                }
            }
        }
    }
}

@Composable
fun QuickActionCard(
    icon: Int,
    label: String,
    tintColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .height(72.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius)),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = dimensionResource(R.dimen.card_elevation)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = dimensionResource(R.dimen.spacing_medium)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_medium))
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(tintColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = label,
                    tint = tintColor,
                    modifier = Modifier.size(22.dp)
                )
            }
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun HomeScreenPreview() {
    AutoDriveTheme {
        HomeScreen()
    }
}
