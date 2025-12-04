package ru.vlyashuk.pointmap.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import ru.vlyashuk.pointmap.presentation.viewModels.PointViewModel
import ru.vlyashuk.pointmap.ui.ui_item.PointFormScreen

@Composable
fun AddPointScreen(
    lat: Double?,
    lon: Double?,
    navController: NavHostController,
    pointViewModel: PointViewModel = hiltViewModel()
) {
    val coordinates = remember(lat, lon) {
        if (lat != null && lon != null) "$lat, $lon" else ""
    }

    PointFormScreen(
        navController = navController,
        initialCoordinates = coordinates,
        onSave = { title, coordinates, description, status ->
            pointViewModel.addPoint(title, coordinates, description, status)
        }
    )
}