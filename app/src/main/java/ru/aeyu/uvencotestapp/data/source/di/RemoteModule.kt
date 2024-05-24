package ru.aeyu.uvencotestapp.data.source.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.aeyu.uvencotestapp.data.source.remote.RemoteDataSource
import ru.aeyu.uvencotestapp.data.source.remote.RemoteDataSourceMockImpl
import java.io.InputStream


@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    fun provideRemoteDataSource(
    ) : RemoteDataSource = RemoteDataSourceMockImpl()


}