package ru.vlyashuk.pointmap.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavScreen(val route: String, val title: String, val icon: ImageVector) {
    object Main : BottomNavScreen(Routes.Main.route ?: "main", "Главная", Icons.Default.Home)
    object Map : BottomNavScreen(Routes.Map.route ?: "map", "Карта", Icons.Default.Place)
    object Profile : BottomNavScreen(Routes.Profile.route ?: "profile", "Профиль", Icons.Default.Person)
}