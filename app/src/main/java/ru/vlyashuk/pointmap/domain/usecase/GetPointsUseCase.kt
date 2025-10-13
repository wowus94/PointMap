package ru.vlyashuk.pointmap.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.vlyashuk.pointmap.data.repository.PointRepository
import ru.vlyashuk.pointmap.domain.model.Point
import javax.inject.Inject

class GetPointsUseCase @Inject constructor(private val repository: PointRepository) {
    operator fun invoke(): Flow<List<Point>> {
        return repository.getAllPoints()
            .map { list -> list.map { it.toDomain() } }
    }
}