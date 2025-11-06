package ru.vlyashuk.pointmap.data.repository

import kotlinx.coroutines.flow.Flow
import ru.vlyashuk.pointmap.data.local.PointDao
import ru.vlyashuk.pointmap.data.local.PointEntity
import javax.inject.Inject

class PointRepositoryImpl @Inject constructor(
    private val dao: PointDao
) : PointRepository {

    override fun getAllPoints(): Flow<List<PointEntity>> = dao.getAllPoints()
    override fun getPointsByStatuses(statuses: List<String>): Flow<List<PointEntity>> =
        dao.getPointsByStatuses(statuses)

    override suspend fun getPointById(id: Long): PointEntity? = dao.getPointById(id)
    override suspend fun insert(point: PointEntity) = dao.insert(point)
    override suspend fun update(point: PointEntity) = dao.update(point)
    override suspend fun delete(point: PointEntity) = dao.delete(point)
    override suspend fun deleteAll() = dao.deleteAll()
}