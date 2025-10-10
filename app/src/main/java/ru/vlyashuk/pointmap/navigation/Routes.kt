package ru.vlyashuk.pointmap.navigation

import androidx.navigation.NavDestination
import kotlinx.serialization.Serializable

class Routes {

    @Serializable
    object Main : NavDestination("main")

    @Serializable
    object Map : NavDestination("map")

    @Serializable
    object Profile : NavDestination("profile")

    @Serializable
    object AddPoint : NavDestination("add_point")

    @Serializable
    data class EditNote(val id: Long)
}