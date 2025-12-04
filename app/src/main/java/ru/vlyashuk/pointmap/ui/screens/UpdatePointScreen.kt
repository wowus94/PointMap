package ru.vlyashuk.pointmap.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import ru.vlyashuk.pointmap.presentation.viewModels.PointViewModel
import ru.vlyashuk.pointmap.ui.ui_item.PointFormScreen

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

    point?.let {
        PointFormScreen(
            navController = navController,
            initialTitle = it.title,
            initialCoordinates = it.coordinates,
            initialDescription = it.description ?: "",
            initialStatus = it.status ?: ""
        ) { title, coordinates, description, status ->
            pointViewModel.updatePoint(
                it.copy(
                    title = title,
                    coordinates = coordinates,
                    description = description,
                    status = status
                )
            )
        }
    }
}