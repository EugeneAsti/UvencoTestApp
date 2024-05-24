package ru.aeyu.uvencotestapp.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.aeyu.uvencotestapp.data.repositories.local.LocalRepository
import ru.aeyu.uvencotestapp.data.repositories.remote.RemoteRepository
import ru.aeyu.uvencotestapp.domain.models.ProductItem
import ru.aeyu.uvencotestapp.domain.models.SavedItemSettings
import ru.aeyu.uvencotestapp.domain.models.toProductItem
import ru.aeyu.uvencotestapp.domain.models.toSavedItemSettings
import ru.aeyu.uvencotestapp.utils.FlowResult

class GetDetailsItemUseCase(
    private val localRepository: LocalRepository,
) {
    operator fun invoke(itemId: Int): Flow<FlowResult<ProductItem>> = flow {

        try {
            val localSettings = localRepository.getLocalItemSettingsById(itemId)
            if (localSettings != null)
                emit(FlowResult.completeSuccess(localSettings.toSavedItemSettings().toProductItem()))
            else {
                emit(FlowResult.completeEmpty(messageResult = "settings not found"))
            }
        } catch (e: Exception) {
            emit(FlowResult.error("Error on getLocal data", e))
        }
    }
}