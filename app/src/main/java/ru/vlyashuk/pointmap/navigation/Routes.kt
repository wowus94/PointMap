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
    object AddNote : NavDestination("add_note")

    @Serializable
    data class EditNote(val id: Long)
}