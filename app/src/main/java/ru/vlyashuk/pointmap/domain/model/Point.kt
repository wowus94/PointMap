package ru.vlyashuk.pointmap.domain.model

data class Point(
    val id: Long = 0L,
    val title: String,
    val coordinates: String,
    val description: String?
)