package ru.vlyashuk.pointmap.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.vlyashuk.pointmap.R
import ru.vlyashuk.pointmap.ui.theme.AppThemeMode

@Composable
fun SettingsScreen(
    appTheme: AppThemeMode,
    onThemeChange: (AppThemeMode) -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        modifier = modifier
    ) { paddingValues ->

        Column(
            modifier = Modifier.padding(paddingValues)
        ) {

            Text(
                "App settings",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                style = MaterialTheme.typography.titleSmall
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            )
            Text(
                "App theme",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                style = MaterialTheme.typography.titleMedium
            )

            SingleChoiceSegmentedButtonRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {

                SegmentedButton(
                    selected = appTheme == AppThemeMode.LIGHT,
                    onClick = { onThemeChange(AppThemeMode.LIGHT) },
                    shape = SegmentedButtonDefaults.itemShape(index = 0, count = 3),
                    label = { Text("Light") },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_light_mode),
                            contentDescription = "Light mode"
                        )
                    }
                )

                SegmentedButton(
                    selected = appTheme == AppThemeMode.DARK,
                    onClick = { onThemeChange(AppThemeMode.DARK) },
                    shape = SegmentedButtonDefaults.itemShape(index = 1, count = 3),
                    label = { Text("Dark") },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_dark_mode),
                            contentDescription = "Dark mode"
                        )
                    }
                )

                SegmentedButton(
                    selected = appTheme == AppThemeMode.SYSTEM,
                    onClick = { onThemeChange(AppThemeMode.SYSTEM) },
                    shape = SegmentedButtonDefaults.itemShape(index = 2, count = 3),
                    label = { Text("System") },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_system_mode),
                            contentDescription = "System mode"
                        )
                    }
                )
            }
        }
    }
}