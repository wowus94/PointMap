package ru.vlyashuk.pointmap.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavScreen(val route: String, val title: String, val icon: ImageVector) {
    object Main :
        BottomNavScreen(Routes.Main.route ?: "main", "List", Icons.AutoMirrored.Default.List)

    object Map : BottomNavScreen(Routes.Map.route ?: "map", "Map", Icons.Default.Place)
    object Profile :
        BottomNavScreen(Routes.Profile.route ?: "settings", "Settings", Icons.Default.Settings)
}