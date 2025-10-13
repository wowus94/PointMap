package ru.vlyashuk.pointmap.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.vlyashuk.pointmap.data.local.AppDatabase
import ru.vlyashuk.pointmap.data.local.PointDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "points_db"
        )
            .fallbackToDestructiveMigration(false)
            .build()

    @Provides
    fun providePointDao(db: AppDatabase): PointDao = db.pointDao()
}