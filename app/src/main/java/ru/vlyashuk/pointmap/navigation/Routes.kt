package ru.vlyashuk.pointmap.navigation

import androidx.navigation.NavDestination
import kotlinx.serialization.Serializable

object Routes {

    @Serializable
    object Main : NavDestination("main")

    @Serializable
    object Map : NavDestination("map")

    @Serializable
    object Profile : NavDestination("settings")

    @Serializable
    object AddPoint : NavDestination("add_point") {

        fun withCoordinates(lat: Double, lon: Double): String =
            "$baseAddPoint?lat=$lat&lon=$lon"
    }

    fun updatePointRoute(id: Long) = updatePoint.replace("{id}", id.toString())
}

const val updatePoint = "update_point/{id}"
const val pattern = "add_point?lat={lat}&lon={lon}"
const val baseAddPoint = "add_point"