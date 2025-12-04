package ru.vlyashuk.pointmap.ui.ui_item

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.vlyashuk.pointmap.R

@Composable
fun PointFormScreen(
    navController: NavHostController,
    initialTitle: String = "",
    initialCoordinates: String = "",
    initialDescription: String = "",
    initialStatus: String = "",
    onSave: (title: String, coordinates: String, description: String, status: String) -> Unit
) {
    var title by remember { mutableStateOf(initialTitle) }
    var coordinates by remember { mutableStateOf(initialCoordinates) }
    var description by remember { mutableStateOf(initialDescription) }
    var status by remember { mutableStateOf(initialStatus) }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                OutlinedTextFieldDefaults(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(stringResource(R.string.title)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextFieldDefaults(
                    value = coordinates,
                    onValueChange = { coordinates = it },
                    label = { Text(stringResource(R.string.coordinates)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextFieldDefaults(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(stringResource(R.string.description)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextFieldDefaults(
                    value = status,
                    onValueChange = { status = it },
                    label = { Text(stringResource(R.string.status)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (title.isNotBlank() && coordinates.isNotBlank()) {
                            onSave(title, coordinates, description, status)
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.save))
                }
            }
        }
    }
}