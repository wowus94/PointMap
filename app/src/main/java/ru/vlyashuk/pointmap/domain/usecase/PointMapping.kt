package ru.vlyashuk.pointmap.domain.usecase

import ru.vlyashuk.pointmap.data.local.PointEntity
import ru.vlyashuk.pointmap.domain.model.Point

fun Point.toEntity() = PointEntity(
    id = id,
    title = title,
    coordinates = coordinates,
    description = description,
    status = status
)

fun PointEntity.toDomain() = Point(
    id = id,
    title = title,
    coordinates = coordinates,
    description = description,
    status = status
)