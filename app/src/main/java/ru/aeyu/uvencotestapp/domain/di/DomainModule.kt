package ru.aeyu.uvencotestapp.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ru.aeyu.uvencotestapp.data.repositories.local.LocalRepository
import ru.aeyu.uvencotestapp.data.repositories.remote.RemoteRepository
import ru.aeyu.uvencotestapp.domain.GetDetailsItemUseCase
import ru.aeyu.uvencotestapp.domain.GetRemoteDataUseCase
import ru.aeyu.uvencotestapp.domain.SaveItemUseCase

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    @ViewModelScoped
    fun provideGetDataUseCase(
        remoteRepository: RemoteRepository,
        localRepository: LocalRepository
    ): GetRemoteDataUseCase = GetRemoteDataUseCase(remoteRepository, localRepository)

    @Provides
    @ViewModelScoped
    fun provideGetLocalSettingUseCase(
        localRepository: LocalRepository
    ): GetDetailsItemUseCase = GetDetailsItemUseCase(localRepository)

    @Provides
    @ViewModelScoped
    fun provideSaveItemUseCase(
        localRepository: LocalRepository
    ): SaveItemUseCase = SaveItemUseCase(localRepository)


}