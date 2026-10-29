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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.tarun.autodrive.R
import com.tarun.autodrive.presentation.Utils.SpacerShow
import com.tarun.autodrive.presentation.model.TripItemModel
import com.tarun.autodrive.ui.theme.AutoDriveTheme

data class VehicleUiModel(
    val id: String,
    val imageRes: Int,
    val vehicleNameRes: Int,
    val regNumberRes: Int,
    val isDefault: Boolean = false
)

private val vehicleList = listOf(
    VehicleUiModel(
        id = "creta",
        imageRes = R.drawable.creta,
        vehicleNameRes = R.string.vehicle_hyundai_creta,
        regNumberRes = R.string.vehicle_hyundai_creta_no,
        isDefault = true
    ),
    VehicleUiModel(
        id = "honda",
        imageRes = R.drawable.creta,
        vehicleNameRes = R.string.vehicle_honda_city,
        regNumberRes = R.string.vehicle_honda_city_no,
        isDefault = false
    ),
    VehicleUiModel(
        id = "kia",
        imageRes = R.drawable.kia,
        vehicleNameRes = R.string.vehicle_kia_seltos,
        regNumberRes = R.string.vehicle_kia_seltos_no,
        isDefault = false
    ),
    VehicleUiModel(
        id = "fortuner",
        imageRes = R.drawable.fortuner,
        vehicleNameRes = R.string.vehicle_toyota_fortuner,
        regNumberRes = R.string.vehicle_toyota_fortuner_no,
        isDefault = false
    ),
    VehicleUiModel(
        id = "defender",
        imageRes = R.drawable.defender,
        vehicleNameRes = R.string.vehicle_land_rover_defender,
        regNumberRes = R.string.vehicle_land_rover_defender_no,
        isDefault = false
    ),
    VehicleUiModel(
        id = "nexon",
        imageRes = R.drawable.nexon,
        vehicleNameRes = R.string.vehicle_tata_nexon,
        regNumberRes = R.string.vehicle_tata_nexon_no,
        isDefault = false
    ),
    VehicleUiModel(
        id = "scorpio",
        imageRes = R.drawable.scorpio,
        vehicleNameRes = R.string.vehicle_mahindra_scorpio,
        regNumberRes = R.string.vehicle_mahindra_scorpio_no,
        isDefault = false
    ),
    VehicleUiModel(
        id = "skoda",
        imageRes = R.drawable.skoda,
        vehicleNameRes = R.string.vehicle_skoda_slavia,
        regNumberRes = R.string.vehicle_skoda_slavia_no,
        isDefault = false
    )
)

@Composable
fun VehiclesScreenContent(
    onAddVehicleClick: () -> Unit = {},
    onVehicleClick: (String) -> Unit = {},
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = dimensionResource(R.dimen.screen_padding_horizontal),

                ),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            // Fixed Header Row (My Vehicles & Search/Add icons)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(
                    text = stringResource(R.string.my_vehicles_title),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = dimensionResource(R.dimen.text_size_headline).value.sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_search_24),
                            contentDescription = stringResource(R.string.search_action),
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(dimensionResource(R.dimen.icon_size_medium))
                        )
                    }
                    IconButton(onClick = onAddVehicleClick) {
                        Icon(
                            painter = painterResource(R.drawable.add),
                            contentDescription = stringResource(R.string.action_add_vehicle),
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(dimensionResource(R.dimen.icon_size_medium))
                        )
                    }
                }
            }

            SpacerShow(R.dimen.spacing_small)

            // Scrollable Column for Vehicle Cards Only
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_medium))
            ) {
                vehicleList.forEach { vehicle ->
                    VehicleItemCard(
                        imageRes = vehicle.imageRes,
                        vehicleName = stringResource(vehicle.vehicleNameRes),
                        regNumber = stringResource(vehicle.regNumberRes),
                        isDefault = vehicle.isDefault,
                        onClick = { onVehicleClick(vehicle.id) }
                    )
                }
            }

            SpacerShow(R.dimen.spacing_small)
        }
    }
}

@Composable
fun VehicleItemCard(
    imageRes: Int,
    vehicleName: String,
    regNumber: String,
    isDefault: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius)),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = dimensionResource(R.dimen.card_elevation)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.spacing_medium)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_medium)),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painter = painterResource(imageRes),
                    contentDescription = vehicleName,
                    modifier = Modifier.size(
                        width = dimensionResource(R.dimen.vehicle_thumb_width),
                        height = dimensionResource(R.dimen.vehicle_thumb_height)
                    )
                )
                Column {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = vehicleName,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        if (isDefault) {
                            Surface(
                                shape = RoundedCornerShape(dimensionResource(R.dimen.badge_corner_radius)),
                                color = colorResource(R.color.accent_green).copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = stringResource(R.string.default_badge),
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                    color = colorResource(R.color.accent_green),
                                    modifier = Modifier.padding(
                                        horizontal = dimensionResource(R.dimen.spacing_small),
                                        vertical = dimensionResource(R.dimen.padding_micro)
                                    )
                                )
                            }
                        }
                    }
                    SpacerShow(R.dimen.spacing_micro)
                    Text(
                        text = regNumber,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (!isDefault) {
                Text(
                    text = stringResource(R.string.chevron_right),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}



@Preview(showSystemUi = true)
@Composable
fun VehiclesScreenContentPreview() {
    AutoDriveTheme {
        VehiclesScreenContent()
    }
}
