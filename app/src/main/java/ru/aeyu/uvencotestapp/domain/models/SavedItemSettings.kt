package ru.aeyu.uvencotestapp.domain.models

import ru.aeyu.uvencotestapp.data.source.local.entities.ItemSettingsEntity
import ru.aeyu.uvencotestapp.domain.enums.CoffeeType

data class SavedItemSettings(
    val id: Int,
    val name: String,
    val price: Int,
    val isForFree: Boolean,
    val coffeeType: CoffeeType,
    val currencySymbol: String,
)

fun ItemSettingsEntity.toSavedItemSettings() : SavedItemSettings =
    SavedItemSettings(id = this.id,
        name = this.name,
        price = this.price,
        isForFree = this.isForFree != 0,
        coffeeType = CoffeeType.getCoffeeType(this.coffeeType),
        currencySymbol = this.currencySymbol
    )