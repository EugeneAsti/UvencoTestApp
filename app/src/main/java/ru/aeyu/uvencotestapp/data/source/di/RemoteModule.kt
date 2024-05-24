package ru.aeyu.uvencotestapp.data.source.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.aeyu.uvencotestapp.data.source.remote.RemoteDataSource
import ru.aeyu.uvencotestapp.data.source.remote.RemoteDataSourceMockImpl


@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    fun provideRemoteDataSource(
    ): RemoteDataSource = RemoteDataSourceMockImpl()


}