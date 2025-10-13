package ru.vlyashuk.pointmap.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.vlyashuk.pointmap.data.repository.PointRepository
import ru.vlyashuk.pointmap.data.repository.PointRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPointRepository(
        impl: PointRepositoryImpl
    ): PointRepository
}