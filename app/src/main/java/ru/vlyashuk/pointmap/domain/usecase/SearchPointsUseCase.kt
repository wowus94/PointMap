package ru.vlyashuk.pointmap.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.vlyashuk.pointmap.data.repository.PointRepository
import ru.vlyashuk.pointmap.domain.model.Point
import javax.inject.Inject

class SearchPointsUseCase @Inject constructor(
    private val repository: PointRepository
) {
    operator fun invoke(query: String): Flow<List<Point>> {
        return repository.searchPoints(query).map { list ->
            list.map { it.toDomain() }
        }
    }
}