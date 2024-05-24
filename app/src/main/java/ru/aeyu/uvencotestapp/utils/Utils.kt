package ru.aeyu.uvencotestapp.utils

import java.text.NumberFormat
import java.util.Locale

fun getCurrencySymbol(): String {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
    return numberFormat.currency?.symbol ?: ""
}