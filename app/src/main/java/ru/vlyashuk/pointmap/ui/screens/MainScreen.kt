package ru.vlyashuk.pointmap.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.vlyashuk.pointmap.navigation.Routes
import ru.vlyashuk.pointmap.presentation.viewModels.PointViewModel
import ru.vlyashuk.pointmap.ui.ui_item.PointItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    pointViewModel: PointViewModel = hiltViewModel()
) {
    val points by pointViewModel.points.collectAsState()
    val query by pointViewModel.searchQuery.collectAsState()

    val filterSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showFilterBottomSheet by remember { mutableStateOf(false) }

    var searchExpanded by remember { mutableStateOf(false) }

    val backgroundColor by animateColorAsState(
        targetValue = if (searchExpanded)
            MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.98f)
        else
            MaterialTheme.colorScheme.background,
        label = "search background animation"
    )

    LaunchedEffect(Unit) {
        pointViewModel.loadPoints()
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(PaddingValues(top = paddingValues.calculateTopPadding()))
        ) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                expanded = searchExpanded,
                onExpandedChange = { searchExpanded = it },
                inputField = {
                    SearchBarDefaults.InputField(
                        query = query,
                        onQueryChange = { pointViewModel.searchPoints(it) },
                        onSearch = { searchExpanded = false },
                        expanded = searchExpanded,
                        onExpandedChange = { searchExpanded = it },
                        placeholder = { Text("Search...") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        },
                        trailingIcon = {
                            Row {
                                if (query.isNotEmpty()) {
                                    IconButton(onClick = { pointViewModel.searchPoints("") }) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Очистить"
                                        )
                                    }
                                }
                                IconButton(onClick = { showFilterBottomSheet = true }) {
                                    Icon(
                                        imageVector = Icons.Default.Settings,
                                        contentDescription = "Фильтр"
                                    )
                                }
                            }
                        }
                    )
                }
            ) {
                if (searchExpanded) {
                    if (points.isEmpty() && query.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Not found «$query»",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f, fill = true),
                            contentPadding = PaddingValues(
                                bottom = paddingValues.calculateBottomPadding() + 80.dp,
                            )
                        ) {
                            items(points) { point ->
                                PointItemCard(
                                    point = point,
                                    modifier = Modifier
                                        .padding(vertical = 4.dp)
                                        .clickable {
                                            searchExpanded = false
                                            navController.navigate(Routes.updatePointRoute(point.id))
                                        },
                                    openUpdateScreen = {
                                        navController.navigate(Routes.updatePointRoute(point.id))
                                    },
                                    onDeleteClick = { pointViewModel.deletePoint(point) }
                                )
                            }
                        }
                    }
                }
            }

            if (!searchExpanded) {
                if (points.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No points added yet",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f, fill = true),
                        contentPadding = PaddingValues(
                            bottom = paddingValues.calculateBottomPadding() + 80.dp
                        )
                    ) {
                        items(points) { point ->
                            PointItemCard(
                                point = point,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                openUpdateScreen = {
                                    navController.navigate(Routes.updatePointRoute(point.id))
                                },
                                onDeleteClick = { pointViewModel.deletePoint(point) }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showFilterBottomSheet) {
        FilterBottomSheet(
            scope = scope,
            filterSheetState = filterSheetState,
            onDismiss = { showFilterBottomSheet = false },
            viewModel = pointViewModel
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    scope: CoroutineScope,
    filterSheetState: SheetState,
    onDismiss: () -> Unit,
    viewModel: PointViewModel = hiltViewModel()
) {
    val availableStatuses by viewModel.availableStatuses.collectAsState()
    val selectedStatuses by viewModel.selectedStatuses.collectAsState()
    val scrollState = rememberScrollState()

    ModalBottomSheet(
        onDismissRequest = {
            scope.launch { filterSheetState.hide() }.invokeOnCompletion {
                if (!filterSheetState.isVisible) {
                    onDismiss()
                }
            }
        },
        sheetState = filterSheetState
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            Text(
                text = "Filter points by status",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(16.dp))

            if (availableStatuses.isEmpty()) {
                Text("No statuses", color = Color.Gray)
            } else {
                availableStatuses.forEach { status ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.toggleStatus(status) }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = selectedStatuses.contains(status),
                            onCheckedChange = { viewModel.toggleStatus(status) }
                        )
                        Text(
                            text = status,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        viewModel.resetFilter()
                        scope.launch { filterSheetState.hide() }.invokeOnCompletion { onDismiss() }
                    }
                ) {
                    Text("Clear filter")
                }

                Button(
                    onClick = {
                        viewModel.applyFilter()
                        scope.launch { filterSheetState.hide() }.invokeOnCompletion { onDismiss() }
                    }
                ) {
                    Text("Apply filter")
                }
            }
        }
    }
}