package ru.vlyashuk.pointmap.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec

@Database(
    entities = [PointEntity::class],
    version = 2,
    autoMigrations = [AutoMigration(
        from = 1,
        to = 2,
        spec = AppDatabase.AddStatusMigrationSpec::class
    )],
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pointDao(): PointDao

    @RenameColumn(
        tableName = "points",
        fromColumnName = "enabled",
        toColumnName = "status"
    )
    class AddStatusMigrationSpec : AutoMigrationSpec
}