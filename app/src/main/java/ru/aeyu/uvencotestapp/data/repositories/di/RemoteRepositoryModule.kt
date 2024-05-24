package ru.aeyu.uvencotestapp.data.repositories.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.aeyu.uvencotestapp.data.repositories.remote.RemoteRepository
import ru.aeyu.uvencotestapp.data.source.remote.RemoteDataSource

@Module
@InstallIn(SingletonComponent::class)
object RemoteRepositoryModule {

    @Provides
    fun provideRemoteRepository(remoteDataSource: RemoteDataSource): RemoteRepository =
        RemoteRepository(remoteDataSource = remoteDataSource)
}