package ru.vlyashuk.pointmap.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PointDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pointEntity: PointEntity): Long

    @Query("SELECT * FROM points ORDER BY id DESC")
    fun getAllPoints(): Flow<List<PointEntity>>

    @Query("SELECT * FROM points WHERE id = :id LIMIT 1")
    suspend fun getPointById(id: Long): PointEntity?

    @Update
    suspend fun update(point: PointEntity)

    @Delete
    suspend fun delete(point: PointEntity)

    @Query("DELETE FROM points")
    suspend fun deleteAll()
}