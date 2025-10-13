package ru.vlyashuk.pointmap.domain.usecase

import ru.vlyashuk.pointmap.data.repository.PointRepository
import ru.vlyashuk.pointmap.domain.model.Point
import javax.inject.Inject

class GetPointByIdUseCase @Inject constructor(
    private val repository: PointRepository
) {
    suspend operator fun invoke(id: Long): Point? {
        val entity = repository.getPointById(id) ?: return null
        return entity.toDomain()
    }
}