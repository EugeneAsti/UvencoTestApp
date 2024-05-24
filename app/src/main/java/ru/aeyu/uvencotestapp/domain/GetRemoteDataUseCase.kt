package ru.aeyu.uvencotestapp.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.aeyu.uvencotestapp.data.repositories.local.LocalRepository
import ru.aeyu.uvencotestapp.data.repositories.remote.RemoteRepository
import ru.aeyu.uvencotestapp.domain.models.ProductItem
import ru.aeyu.uvencotestapp.domain.models.SavedItemSettings
import ru.aeyu.uvencotestapp.domain.models.toProductItem
import ru.aeyu.uvencotestapp.domain.models.toSavedItemSettings

class GetRemoteDataUseCase(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) {
    operator fun invoke(): Flow<List<ProductItem>> = flow {

        val localDataSize = localRepository.countSavedSettings()
        val localMap: HashMap<Int, SavedItemSettings> = HashMap(localDataSize)
        if (localDataSize != 0) {

            localRepository.getLocalItemSettingsAll().map {
                it.toSavedItemSettings()
            }.forEach {
                localMap[it.id] = it
            }
        }
        val result = remoteRepository.getRemoteData().map { remoteItem ->
            remoteItem.toProductItem(localMap[remoteItem.id])
        }

        emit(result)
    }
}