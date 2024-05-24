package ru.aeyu.uvencotestapp.ui.screens.details.models

import ru.aeyu.uvencotestapp.domain.models.ProductItem
import ru.aeyu.uvencotestapp.ui.base.UiState

data class DetailsScreenState(
    val curProductItem: ProductItem? = null,
    val newProductItem: ProductItem? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val hasChanges: Boolean = false,
    val hasNameError: Boolean = false,
    val hasPriceError: Boolean = false,
    ) : UiState