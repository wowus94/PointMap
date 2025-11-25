package ru.vlyashuk.pointmap.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import ru.vlyashuk.pointmap.R

sealed class BottomNavScreen(val route: String, @StringRes val title: Int, val icon: ImageVector) {
    object Main :
        BottomNavScreen(
            Routes.Main.route ?: "main",
            R.string.list,
            Icons.AutoMirrored.Default.List
        )

    object Map : BottomNavScreen(
        Routes.Map.route ?: "map",
        R.string.map,
        Icons.Default.Place
    )

    object Profile :
        BottomNavScreen(
            Routes.Profile.route ?: "settings",
            R.string.settings,
            Icons.Default.Settings
        )
}