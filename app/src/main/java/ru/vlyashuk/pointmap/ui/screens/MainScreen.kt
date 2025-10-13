package ru.vlyashuk.pointmap.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import ru.vlyashuk.pointmap.navigation.Routes
import ru.vlyashuk.pointmap.presentation.viewModels.PointViewModel
import ru.vlyashuk.pointmap.ui.ui_item.PointItemCard

@Composable
fun MainScreen(
    navController: NavHostController,
    pointViewModel: PointViewModel = hiltViewModel()
) {

    val points by pointViewModel.points.collectAsState()

    LaunchedEffect(Unit) {
        pointViewModel.loadPoints()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn {
            items(points) { point ->
                PointItemCard(
                    point = point,
                    modifier = Modifier.padding(8.dp),
                    openUpdateScreen = { navController.navigate(Routes.updatePointRoute(point.id)) },
                    onDeleteClick = { pointViewModel.deletePoint(point) }
                )
            }
        }
    }
}