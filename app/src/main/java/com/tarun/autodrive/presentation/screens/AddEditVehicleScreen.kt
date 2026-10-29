package com.tarun.autodrive.presentation.screens

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil3.compose.AsyncImage
import com.tarun.autodrive.R
import com.tarun.autodrive.presentation.Utils.AppOutlinedTextField
import com.tarun.autodrive.presentation.Utils.RequiredLabel
import com.tarun.autodrive.presentation.Utils.SpacerShow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditVehicleScreen(
    vehicleId: String? = null,
    onBackClick: () -> Unit = {},
    onSaveSuccess: () -> Unit = {}
) {
    val context = LocalContext.current
    val isEditMode = vehicleId != null && vehicleId.isNotBlank()

    var vehicleName by remember { mutableStateOf(if (isEditMode) "Hyundai Creta" else "") }
    var model by remember { mutableStateOf(if (isEditMode) "SX (O)" else "") }
    var selectedYear by remember { mutableStateOf(if (isEditMode) "2023" else "") }
    var fuelType by remember { mutableStateOf(if (isEditMode) "Petrol" else "") }
    var regNumber by remember { mutableStateOf(if (isEditMode) "KA 01 AB 1234" else "") }

    var vehicleNameError by remember { mutableStateOf<String?>(null) }
    var modelError by remember { mutableStateOf<String?>(null) }
    var yearError by remember { mutableStateOf<String?>(null) }
    var fuelTypeError by remember { mutableStateOf<String?>(null) }
    var regNumberError by remember { mutableStateOf<String?>(null) }

    // Image state (can be Uri or Bitmap)
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // Dialog state for image picker
    var showImageSourceDialog by remember { mutableStateOf(false) }

    // Dropdown states
    var isYearExpanded by remember { mutableStateOf(false) }
    var isFuelExpanded by remember { mutableStateOf(false) }

    val years = listOf("2025", "2024", "2023", "2022", "2021", "2020", "2019", "2018", "2017", "2016")
    val fuelTypes = listOf("Petrol", "Diesel", "Electric", "CNG", "Hybrid")

    // Launchers for Gallery and Camera
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            imageUri = uri
            imageBitmap = null
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            imageBitmap = bitmap
            imageUri = null
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraLauncher.launch(null)
        } else {
            Toast.makeText(context, "Camera permission is required to take photo", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isEditMode) stringResource(R.string.edit_vehicle_title) else stringResource(R.string.add_vehicle_title),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(R.drawable.back_arrow),
                            contentDescription = stringResource(R.string.legend_back),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            var isValid = true

                            if (vehicleName.isBlank()) {
                                vehicleNameError = "Vehicle name cannot be empty"
                                isValid = false
                            } else {
                                vehicleNameError = null
                            }

                            if (model.isBlank()) {
                                modelError = "Model cannot be empty"
                                isValid = false
                            } else {
                                modelError = null
                            }

                            if (selectedYear.isBlank()) {
                                yearError = "Please select a year"
                                isValid = false
                            } else {
                                yearError = null
                            }

                            if (fuelType.isBlank()) {
                                fuelTypeError = "Please select a fuel type"
                                isValid = false
                            } else {
                                fuelTypeError = null
                            }

                            if (regNumber.isBlank()) {
                                regNumberError = "Registration number cannot be empty"
                                isValid = false
                            } else {
                                regNumberError = null
                            }

                            if (isValid) {
                                val msg = if (isEditMode) "Vehicle updated successfully!" else "Vehicle saved successfully!"
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                onSaveSuccess()
                            }
                        }
                    ) {
                        Text(
                            text = if (isEditMode) stringResource(R.string.update_action) else stringResource(R.string.save_action),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
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
            // Vehicle Image Picker Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.input_corner_radius)))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable { showImageSourceDialog = true },
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = "Vehicle Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else if (imageBitmap != null) {
                    Image(
                        bitmap = imageBitmap!!.asImageBitmap(),
                        contentDescription = "Vehicle Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.vehicle),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(40.dp)
                        )
                        SpacerShow(R.dimen.spacing_small)
                        Text(
                            text = stringResource(R.string.add_vehicle_image),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Vehicle Name
            RequiredLabel(textResId = R.string.vehicle_name_label)
            AppOutlinedTextField(
                value = vehicleName,
                onValueChange = {
                    vehicleName = it
                    vehicleNameError = null
                },
                placeholderText = stringResource(R.string.vehicle_name_hint),
                isError = vehicleNameError != null,
                errorMessage = vehicleNameError
            )

            // Model
            RequiredLabel(textResId = R.string.model_label)
            AppOutlinedTextField(
                value = model,
                onValueChange = {
                    model = it
                    modelError = null
                },
                placeholderText = stringResource(R.string.model_hint),
                isError = modelError != null,
                errorMessage = modelError
            )

            // Year Dropdown
            RequiredLabel(textResId = R.string.year_label)
            ExposedDropdownMenuBox(
                expanded = isYearExpanded,
                onExpandedChange = { isYearExpanded = it },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedYear,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text(stringResource(R.string.year_hint)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isYearExpanded) },
                    isError = yearError != null,
                    modifier = Modifier
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(dimensionResource(R.dimen.input_corner_radius)),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    )
                )
                ExposedDropdownMenu(
                    expanded = isYearExpanded,
                    onDismissRequest = { isYearExpanded = false }
                ) {
                    years.forEach { year ->
                        DropdownMenuItem(
                            text = { Text(year) },
                            onClick = {
                                selectedYear = year
                                isYearExpanded = false
                                yearError = null
                            }
                        )
                    }
                }
            }
            if (yearError != null) {
                Text(
                    text = yearError!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Fuel Type Dropdown
            RequiredLabel(textResId = R.string.fuel_type_label)
            ExposedDropdownMenuBox(
                expanded = isFuelExpanded,
                onExpandedChange = { isFuelExpanded = it },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = fuelType,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text(stringResource(R.string.fuel_type_hint)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isFuelExpanded) },
                    isError = fuelTypeError != null,
                    modifier = Modifier
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(dimensionResource(R.dimen.input_corner_radius)),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    )
                )
                ExposedDropdownMenu(
                    expanded = isFuelExpanded,
                    onDismissRequest = { isFuelExpanded = false }
                ) {
                    fuelTypes.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type) },
                            onClick = {
                                fuelType = type
                                isFuelExpanded = false
                                fuelTypeError = null
                            }
                        )
                    }
                }
            }
            if (fuelTypeError != null) {
                Text(
                    text = fuelTypeError!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Registration Number
            RequiredLabel(textResId = R.string.reg_number_label)
            AppOutlinedTextField(
                value = regNumber,
                onValueChange = {
                    regNumber = it
                    regNumberError = null
                },
                placeholderText = stringResource(R.string.reg_number_hint),
                isError = regNumberError != null,
                errorMessage = regNumberError
            )

            SpacerShow(R.dimen.spacing_large)
        }
    }

    // Image Source Selection Dialog
    if (showImageSourceDialog) {
        AlertDialog(
            onDismissRequest = { showImageSourceDialog = false },
            title = { Text(text = "Select Vehicle Image") },
            text = { Text(text = "Choose a source to add your vehicle picture.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showImageSourceDialog = false
                        val permissionCheck = ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.CAMERA
                        )
                        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                            cameraLauncher.launch(null)
                        } else {
                            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    }
                ) {
                    Text("Take Photo")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showImageSourceDialog = false
                        galleryLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                ) {
                    Text("Choose from Gallery")
                }
            }
        )
    }
}
