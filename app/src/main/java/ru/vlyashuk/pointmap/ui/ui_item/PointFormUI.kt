package ru.vlyashuk.pointmap.ui.ui_item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import ru.vlyashuk.pointmap.R
import ru.vlyashuk.pointmap.presentation.viewModels.PointFormViewModel

@Composable
fun PointFormScreen(
    navController: NavHostController,
    initialTitle: String = "",
    initialCoordinates: String = "",
    initialDescription: String = "",
    initialStatus: String = "",
    viewModel: PointFormViewModel = hiltViewModel(),
    onSave: (title: String, coordinates: String, description: String, status: String) -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        if (initialTitle.isNotEmpty()) viewModel.onTitleChanged(initialTitle)
        if (initialCoordinates.isNotEmpty()) viewModel.onCoordinatesChanged(initialCoordinates)
        if (initialDescription.isNotEmpty()) viewModel.onDescriptionChanged(initialDescription)
        if (initialStatus.isNotEmpty()) viewModel.onStatusChanged(initialStatus)
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                OutlinedTextFieldDefaults(
                    value = state.title,
                    onValueChange = { viewModel.onTitleChanged(it) },
                    label = { Text(stringResource(R.string.title)) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = state.titleError != null,
                    supportingText = {
                        state.titleError?.let {
                            Text(
                                it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextFieldDefaults(
                    value = state.coordinates,
                    onValueChange = { viewModel.onCoordinatesChanged(it) },
                    label = { Text(stringResource(R.string.coordinates)) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = state.coordinatesError != null,
                    supportingText = {
                        state.coordinatesError?.let {
                            Text(
                                it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextFieldDefaults(
                    value = state.description,
                    onValueChange = { viewModel.onDescriptionChanged(it) },
                    label = { Text(stringResource(R.string.description)) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = state.descriptionError != null,
                    supportingText = {
                        state.descriptionError?.let {
                            Text(
                                it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextFieldDefaults(
                    value = state.status,
                    onValueChange = { viewModel.onStatusChanged(it) },
                    label = { Text(stringResource(R.string.status)) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = state.statusError != null,
                    supportingText = {
                        state.statusError?.let {
                            Text(
                                it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = {
                        onSave(state.title, state.coordinates, state.description, state.status)
                        navController.popBackStack()
                    },
                    enabled = state.isValid,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.save))
                }
            }
        }
    }
}

data class PointFormState(
    val title: String = "",
    val coordinates: String = "",
    val description: String = "",
    val status: String = "",

    val titleError: String? = null,
    val coordinatesError: String? = null,
    val descriptionError: String? = null,
    val statusError: String? = null,

    val isValid: Boolean = false
)