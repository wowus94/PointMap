package ru.vlyashuk.pointmap.data.repository

import kotlinx.coroutines.flow.Flow
import ru.vlyashuk.pointmap.data.local.PointEntity


interface PointRepository {
    fun getAllPoints(): Flow<List<PointEntity>>
    fun getPointsByStatuses(statuses: List<String>): Flow<List<PointEntity>>
    suspend fun getPointById(id: Long): PointEntity?
    suspend fun insert(point: PointEntity): Long
    suspend fun update(point: PointEntity)
    suspend fun delete(point: PointEntity)
    suspend fun deleteAll()
}