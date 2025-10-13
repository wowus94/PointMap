package ru.vlyashuk.pointmap.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import ru.vlyashuk.pointmap.presentation.viewModels.PointViewModel

@Composable
fun UpdatePointScreen(
    navController: NavHostController,
    pointViewModel: PointViewModel = hiltViewModel(),
    pointId: Long
) {
    val point by pointViewModel.selectedPoint.collectAsState()

    LaunchedEffect(pointId) {
        pointViewModel.loadPointById(pointId)
    }

    point?.let { point ->
        var title by remember { mutableStateOf(point.title) }
        var coordinates by remember { mutableStateOf(point.coordinates) }
        var description by remember { mutableStateOf(point.description ?: "") }

        Column(modifier = Modifier.padding(16.dp)) {

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = coordinates,
                onValueChange = { coordinates = it },
                label = { Text("Coordinates") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (title.isNotBlank() && coordinates.isNotBlank()) {
                        pointViewModel.updatePoint(
                            point.copy(
                                title = title,
                                coordinates = coordinates,
                                description = description
                            )
                        )
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    } ?: Text("Loading...")
}