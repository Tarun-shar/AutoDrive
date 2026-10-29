package com.tarun.autodrive.presentation.screens

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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tarun.autodrive.R
import com.tarun.autodrive.presentation.Utils.SpacerShow

@Composable
fun VehicleDetailsScreen(
    vehicleId: String = "creta",
    onBackClick: () -> Unit = {},
    onEditClick: (String) -> Unit = {}
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Overview, 1: Info, 2: Documents

    // Vehicle details based on vehicleId
    val (vehicleName, regNumber, imageRes) = when (vehicleId.lowercase()) {
        "honda" -> Triple(stringResource(R.string.vehicle_honda_city), stringResource(R.string.vehicle_honda_city_no), R.drawable.creta)
        "kia" -> Triple(stringResource(R.string.vehicle_kia_seltos), stringResource(R.string.vehicle_kia_seltos_no), R.drawable.kia)
        "fortuner" -> Triple(stringResource(R.string.vehicle_toyota_fortuner), stringResource(R.string.vehicle_toyota_fortuner_no), R.drawable.fortuner)
        "defender" -> Triple(stringResource(R.string.vehicle_land_rover_defender), stringResource(R.string.vehicle_land_rover_defender_no), R.drawable.defender)
        "nexon" -> Triple(stringResource(R.string.vehicle_tata_nexon), stringResource(R.string.vehicle_tata_nexon_no), R.drawable.nexon)
        "scorpio" -> Triple(stringResource(R.string.vehicle_mahindra_scorpio), stringResource(R.string.vehicle_mahindra_scorpio_no), R.drawable.scorpio)
        "skoda" -> Triple(stringResource(R.string.vehicle_skoda_slavia), stringResource(R.string.vehicle_skoda_slavia_no), R.drawable.skoda)
        else -> Triple(stringResource(R.string.vehicle_hyundai_creta), stringResource(R.string.vehicle_hyundai_creta_no), R.drawable.creta)
    }

    Scaffold(

        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(R.dimen.screen_padding_vertical))
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth() ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(R.drawable.back_arrow),
                            contentDescription = stringResource(R.string.legend_back),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 48.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = vehicleName,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = regNumber,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Tabs Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimensionResource(R.dimen.screen_padding_horizontal)),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    TabItem(title = stringResource(R.string.tab_overview), isSelected = selectedTab == 0) { selectedTab = 0 }
                    TabItem(title = stringResource(R.string.tab_info), isSelected = selectedTab == 1) { selectedTab = 1 }
                    TabItem(title = stringResource(R.string.tab_documents), isSelected = selectedTab == 2) { selectedTab = 2 }
                }

                HorizontalDivider(
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.spacing_small)),
                    thickness = dimensionResource(R.dimen.divider_thickness),
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            }
        },
        bottomBar = {
            if (selectedTab == 0) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Button(
                        onClick = { onEditClick(vehicleId) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(R.dimen.screen_padding_horizontal))
                            .height(dimensionResource(R.dimen.button_height)),
                        shape = RoundedCornerShape(dimensionResource(R.dimen.button_corner_radius)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.edit_vehicle_title),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = dimensionResource(R.dimen.screen_padding_horizontal), vertical = dimensionResource(R.dimen.screen_padding_vertical)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_medium))
        ) {
            // Vehicle Image Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.input_corner_radius)))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(imageRes),
                    contentDescription = vehicleName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.spacing_medium))
                )
            }

            when (selectedTab) {
                0 -> OverviewTabContent()
                1 -> InfoTabContent()
                2 -> DocumentsTabContent()
            }
        }
    }
}

@Composable
fun TabItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(vertical = dimensionResource(R.dimen.spacing_small)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            ),
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )
        if (isSelected) {
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(2.dp)
                    .background(MaterialTheme.colorScheme.primary)
            )
        }
    }
}

@Composable
fun OverviewTabContent() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small))
        ) {
            OverviewCard(
                modifier = Modifier.weight(1f),
                iconRes = R.drawable.vehicle,
                label = stringResource(R.string.odometer_label),
                value = "12,456 km"
            )
            OverviewCard(
                modifier = Modifier.weight(1f),
                iconRes = R.drawable.notification,
                label = stringResource(R.string.fuel_type_label),
                value = "Petrol"
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small))
        ) {
            OverviewCard(
                modifier = Modifier.weight(1f),
                iconRes = R.drawable.trips,
                label = stringResource(R.string.insurance_expiry_label),
                value = "15 Aug 2024"
            )
            OverviewCard(
                modifier = Modifier.weight(1f),
                iconRes = R.drawable.notification,
                label = stringResource(R.string.puc_expiry_label),
                value = "10 Jun 2024"
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small))
        ) {
            OverviewCard(
                modifier = Modifier.weight(1f),
                iconRes = R.drawable.profile,
                label = stringResource(R.string.last_service_label),
                value = "15 Apr 2024"
            )
            OverviewCard(
                modifier = Modifier.weight(1f),
                iconRes = R.drawable.home,
                label = stringResource(R.string.next_service_label),
                value = "21 May 2024"
            )
        }
    }
}

@Composable
fun OverviewCard(
    modifier: Modifier = Modifier,
    iconRes: Int,
    label: String,
    value: String
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(dimensionResource(R.dimen.input_corner_radius)),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.spacing_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_micro))
        ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun InfoTabContent() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small))
    ) {
        InfoCard(iconRes = R.drawable.profile, label = stringResource(R.string.owner_name_label), value = "Arjun Reddy")
        InfoCard(iconRes = R.drawable.notification, label = stringResource(R.string.phone_number_label), value = "+91 98765 43210")
        InfoCard(iconRes = R.drawable.trips, label = "Email", value = "arjun.reddy@email.com")
        InfoCard(iconRes = R.drawable.home, label = stringResource(R.string.purchase_date_label), value = "10 Jan 2022")
        InfoCard(iconRes = R.drawable.vehicle, label = stringResource(R.string.purchase_price_label), value = "₹ 12,45,000")
        InfoCard(iconRes = R.drawable.notification, label = stringResource(R.string.chassis_number_label), value = "MA1ABOJ3CD4EF567890")
        InfoCard(iconRes = R.drawable.profile, label = stringResource(R.string.engine_number_label), value = "EN12CD34567890")
    }
}

@Composable
fun InfoCard(
    iconRes: Int,
    label: String,
    value: String
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(R.dimen.input_corner_radius)),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.spacing_medium)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_medium))
        ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
fun DocumentsTabContent() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small))
    ) {
        DocumentItemCard(R.drawable.pdf_icon, title = stringResource(R.string.doc_insurance_cert), fileName = "insurance_creta.pdf", fileSize = "1.2 MB")
        DocumentItemCard(R.drawable.pdf_icon2, title = stringResource(R.string.doc_rc_book), fileName = "rc_book_creta.pdf", fileSize = "2.4 MB")
        DocumentItemCard(R.drawable.pdf_icon, title = "PUC Certificate", fileName = "puc_creta.pdf", fileSize = "1.1 MB")
        DocumentItemCard(R.drawable.pdf_icon2, title = "Driving License", fileName = "driving_license.pdf", fileSize = "1.5 MB")
    }
}

@Composable
fun DocumentItemCard(
    iconRes: Int,
    title: String,
    fileName: String,
    fileSize: String
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(R.dimen.input_corner_radius)),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.spacing_medium)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_medium))
        ) {
            Surface(
                modifier = Modifier.size(40.dp),
                shape = RoundedCornerShape(8.dp),
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(iconRes),
                        contentDescription = "PDF Document",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "$fileName • $fileSize",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.menu),
                    contentDescription = "More Options",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
