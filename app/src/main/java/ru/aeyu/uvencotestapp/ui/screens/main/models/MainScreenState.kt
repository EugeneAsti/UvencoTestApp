package ru.aeyu.uvencotestapp.ui.screens.main.models

import ru.aeyu.uvencotestapp.domain.models.ProductItem
import ru.aeyu.uvencotestapp.ui.base.UiState
import java.text.NumberFormat
import java.util.Locale

data class MainScreenState(
    val products: List<ProductItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: Throwable? = null,
) : UiState