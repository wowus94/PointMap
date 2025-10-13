package ru.vlyashuk.pointmap.domain.usecase

import ru.vlyashuk.pointmap.data.repository.PointRepository
import ru.vlyashuk.pointmap.domain.model.Point
import javax.inject.Inject

class DeletePointUseCase @Inject constructor(private val repository: PointRepository) {
    suspend operator fun invoke(point: Point) {
        repository.delete(point.toEntity())
    }
}