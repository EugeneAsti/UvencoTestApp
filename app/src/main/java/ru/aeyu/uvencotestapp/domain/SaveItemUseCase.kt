package ru.aeyu.uvencotestapp.domain

import ru.aeyu.uvencotestapp.data.repositories.local.LocalRepository
import ru.aeyu.uvencotestapp.domain.models.ProductItem
import ru.aeyu.uvencotestapp.domain.models.toEntity
import java.lang.Exception

class SaveItemUseCase(
    private val localRepository: LocalRepository
) {
    operator fun invoke(productItem: ProductItem): Boolean {
        return try{
            localRepository.saveItemSettings(productItem.toEntity())
            true
        } catch (ex: Exception){
            false
        }
    }
}