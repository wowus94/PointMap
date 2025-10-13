package ru.vlyashuk.pointmap.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PointEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pointDao(): PointDao
}