package ru.aeyu.uvencotestapp.domain.enums

import java.util.Locale

enum class CoffeeType {
    CAPPUCCINO,
    ESPRESSO;

    companion object{
        fun getCoffeeType(name: String) : CoffeeType{
            return when(name.lowercase(Locale.getDefault())){
                "cappuccino" -> CAPPUCCINO
                else -> ESPRESSO
            }
        }
    }
}
