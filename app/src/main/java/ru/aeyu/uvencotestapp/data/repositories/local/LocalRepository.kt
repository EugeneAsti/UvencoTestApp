package ru.aeyu.uvencotestapp.data.repositories.local

import ru.aeyu.uvencotestapp.data.source.local.ItemSettingsDao
import ru.aeyu.uvencotestapp.data.source.local.entities.ItemSettingsEntity

class LocalRepository(
    private val itemSettingsDao: ItemSettingsDao
) {

    fun getLocalItemSettingsById(itemId: Int): ItemSettingsEntity? =
        itemSettingsDao.getDetailsSettingsById(itemId)

    fun getLocalItemSettingsAll(): List<ItemSettingsEntity> =
        itemSettingsDao.getDetailsSettings() ?: emptyList()

    fun countSavedSettings(): Int = itemSettingsDao.countLocalSavedSettings()

    fun saveItemSettings(itemSettings: ItemSettingsEntity) =
        itemSettingsDao.saveItemSettings(itemSettings)

}