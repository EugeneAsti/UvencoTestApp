package ru.aeyu.uvencotestapp.data.source.remote

import ru.aeyu.uvencotestapp.data.source.remote.models.ProductItemRemote
import ru.aeyu.uvencotestapp.domain.enums.CoffeeType
import ru.aeyu.uvencotestapp.utils.getCurrencySymbol

class RemoteDataSourceMockImpl(
) : RemoteDataSource {
    override fun getRemoteData(): List<ProductItemRemote> {
        val currencySymbol = getCurrencySymbol()
        return List(154) { index ->
            ProductItemRemote(
                id = index,
                name = "Капучино эконом",
                price = 199,
                isForFree = false,
                coffeeType = CoffeeType.CAPPUCCINO,
                currencySymbol = currencySymbol
            )
        }
    }
}