package ru.vlyashuk.pointmap.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.vlyashuk.pointmap.data.repository.PointRepository
import ru.vlyashuk.pointmap.domain.model.Point
import javax.inject.Inject

class GetPointsByStatusesUseCase @Inject constructor(
    private val repository: PointRepository
) {
    operator fun invoke(statuses: List<String>): Flow<List<Point>> {
        return repository.getPointsByStatuses(statuses)
            .map { entities -> entities.map { it.toDomain() } }
    }
}