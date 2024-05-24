package ru.aeyu.uvencotestapp.data.source.remote.models

import ru.aeyu.uvencotestapp.domain.enums.CoffeeType

data class ProductItemRemote(
    val id: Int,
    val name: String?  = "undefined",
    val price: Int = 0,
    val isForFree: Boolean? = false,
    val coffeeType: CoffeeType? = CoffeeType.CAPPUCCINO,
    val currencySymbol: String? = ""
)
