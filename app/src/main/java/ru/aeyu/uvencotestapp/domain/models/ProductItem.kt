package ru.aeyu.uvencotestapp.domain.models

import ru.aeyu.uvencotestapp.data.source.local.entities.ItemSettingsEntity
import ru.aeyu.uvencotestapp.data.source.remote.models.ProductItemRemote
import ru.aeyu.uvencotestapp.domain.enums.CoffeeType
import java.util.Objects

data class ProductItem(
    val id: Int = -1,
    val name: String = "",
    val price: Int = 0,
    val isForFree: Boolean = false,
    val coffeeType: CoffeeType = CoffeeType.CAPPUCCINO,
    val currencySymbol: String
) {
    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        return if (other is ProductItem)
            other.id == this.id
                    && other.name == this.name
                    && other.price == this.price
                    && other.isForFree == this.isForFree
                    && other.coffeeType == this.coffeeType
        else
            false
    }

    override fun hashCode(): Int {
        return Objects.hash(this.id, this.name, this.price, this.isForFree, this.coffeeType)
    }
}

fun ProductItemRemote.toProductItem(savedItemSettings: SavedItemSettings? = null): ProductItem {

    return if (savedItemSettings == null)
        ProductItem(
            id = this.id,
            name = this.name!!,
            price = this.price,
            isForFree = this.isForFree!!,
            coffeeType = this.coffeeType!!,
            currencySymbol = this.currencySymbol!!
        )
    else
        ProductItem(
            id = savedItemSettings.id,
            name = savedItemSettings.name,
            price = savedItemSettings.price,
            isForFree = savedItemSettings.isForFree,
            coffeeType = savedItemSettings.coffeeType,
            currencySymbol = savedItemSettings.currencySymbol
        )
}

fun ProductItem.toEntity(): ItemSettingsEntity =
    ItemSettingsEntity(
        id = this.id,
        name = this.name,
        price = this.price,
        isForFree = if (this.isForFree) 1 else 0,
        coffeeType = this.coffeeType.name,
        currencySymbol = this.currencySymbol
    )

fun SavedItemSettings.toProductItem(): ProductItem =
    ProductItem(
        id = this.id,
        name = this.name,
        price = this.price,
        isForFree = this.isForFree,
        coffeeType = this.coffeeType,
        currencySymbol = this.currencySymbol
    )