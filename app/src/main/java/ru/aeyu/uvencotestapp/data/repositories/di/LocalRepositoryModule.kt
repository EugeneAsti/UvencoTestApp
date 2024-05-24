package ru.aeyu.uvencotestapp.data.repositories.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.aeyu.uvencotestapp.data.repositories.local.LocalRepository
import ru.aeyu.uvencotestapp.data.source.local.ItemSettingsDao

@Module
@InstallIn(SingletonComponent::class)
object LocalRepositoryModule {

    @Provides
    fun provideLocalRepository(itemSettingsDao: ItemSettingsDao): LocalRepository =
        LocalRepository(itemSettingsDao)
}