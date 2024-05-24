package ru.aeyu.uvencotestapp.data.source.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ItemSettings")
data class ItemSettingsEntity(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "ProductId") val id: Int,
    @ColumnInfo(name = "ProductName") val name: String,
    @ColumnInfo(name = "ProductPrice") val price: Int,
    @ColumnInfo(name = "NoPrice") val isForFree: Int,
    @ColumnInfo(name = "CoffeeType") val coffeeType: String,
    @ColumnInfo(name = "CurrencySymbol") val currencySymbol: String
)