package ru.aeyu.uvencotestapp.data.source.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ru.aeyu.uvencotestapp.data.source.local.entities.ItemSettingsEntity


@Dao
interface ItemSettingsDao {

    @Query(value = "SELECT * FROM ItemSettings")
    fun getDetailsSettings(): List<ItemSettingsEntity>?

    @Query(value = "SELECT * FROM ItemSettings WHERE ProductId = :id")
    fun getDetailsSettingsById(id: Int): ItemSettingsEntity?

    @Query(value = "SELECT COUNT(ProductId) FROM ItemSettings")
    fun countLocalSavedSettings() : Int

    @Upsert(entity = ItemSettingsEntity::class)
    fun saveItemSettings(itemSettingsEntity: ItemSettingsEntity)
}