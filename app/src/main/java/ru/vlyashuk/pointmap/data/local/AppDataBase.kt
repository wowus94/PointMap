package ru.vlyashuk.pointmap.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PointEntity::class],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pointDao(): PointDao
}