package ru.vlyashuk.pointmap.navigation

import androidx.navigation.NavDestination
import kotlinx.serialization.Serializable

object Routes {

    @Serializable
    object Main : NavDestination("main")

    @Serializable
    object Map : NavDestination("map")

    @Serializable
    object Profile : NavDestination("profile")

    @Serializable
    object AddPoint : NavDestination("add_point")

    const val UpdatePoint = "update_point/{id}"

    fun updatePointRoute(id: Long) = "update_point/$id"
}