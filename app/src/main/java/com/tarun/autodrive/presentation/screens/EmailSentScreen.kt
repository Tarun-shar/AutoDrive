package com.tarun.autodrive.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.tarun.autodrive.R
import com.tarun.autodrive.presentation.Utils.SpacerShow
import com.tarun.autodrive.ui.theme.AutoDriveTheme

@Composable
fun EmailSentScreen(
    email: String = "",
    onBackClick: () -> Unit = {},
    onBackToLoginClick: () -> Unit = {},
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
                    vertical = dimensionResource(R.dimen.screen_padding_vertical)
                ),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            // Back button with negative horizontal offset to align flush with screen edge padding
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.offset(x = dimensionResource(R.dimen.back_button_offset_x))
            ) {
                Icon(
                    painter = painterResource(R.drawable.back_arrow),
                    contentDescription = stringResource(R.string.legend_back),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(R.drawable.checked_icon),
                    contentDescription = "checked_icon",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(
                            height = dimensionResource(R.dimen.image_height_large),
                            width = dimensionResource(R.dimen.image_width_large)
                        )
                )

                SpacerShow(R.dimen.spacing_extra_large)

                Text(
                    text = stringResource(R.string.email_sent_title),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = dimensionResource(R.dimen.text_size_headline).value.sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )

                SpacerShow(R.dimen.spacing_medium)

                Text(
                    text = stringResource(R.string.forgot_password_subtitle),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = dimensionResource(R.dimen.text_size_title).value.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                SpacerShow(R.dimen.spacing_medium)

                Text(
                    text = email,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = dimensionResource(R.dimen.text_size_title).value.sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Back to Login Button
            Button(
                onClick = onBackToLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.button_height)),
                shape = RoundedCornerShape(dimensionResource(R.dimen.button_corner_radius)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = stringResource(R.string.back_to_login),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            SpacerShow(R.dimen.spacing_extra_large)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun EmailSentScreenPreview() {
    AutoDriveTheme {
        EmailSentScreen()
    }
}
