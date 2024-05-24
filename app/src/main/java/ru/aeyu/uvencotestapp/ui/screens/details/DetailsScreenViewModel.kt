package ru.aeyu.uvencotestapp.ui.screens.details

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.aeyu.uvencotestapp.domain.GetDetailsItemUseCase
import ru.aeyu.uvencotestapp.domain.SaveItemUseCase
import ru.aeyu.uvencotestapp.domain.enums.CoffeeType
import ru.aeyu.uvencotestapp.domain.models.ProductItem
import ru.aeyu.uvencotestapp.ui.base.BaseViewModel
import ru.aeyu.uvencotestapp.ui.navigation.ScreenDetails
import ru.aeyu.uvencotestapp.ui.screens.details.models.DetailsScreenState
import ru.aeyu.uvencotestapp.utils.onEmpty
import ru.aeyu.uvencotestapp.utils.onError
import ru.aeyu.uvencotestapp.utils.onSuccess
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val getDetailsItemUseCase: GetDetailsItemUseCase,
    private val saveItemUseCase: SaveItemUseCase,
    app: Application,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<DetailsScreenState>(app) {


    private val itemId: Int = savedStateHandle.get<Int>(ScreenDetails.ITEM_ID_ARG) ?: -1

    override fun setInitialState(): DetailsScreenState = DetailsScreenState()
    override fun clearError() {
        updateState {
            it.copy(error = null)
        }
    }

    override fun processCoroutineErrors(throwable: Throwable) {
        println("!!!###!!! err in coroutine DetailsScreenViewModel")
        throwable.printStackTrace()
        updateState {
            it.copy(
                isLoading = false,
                error = throwable
            )
        }
    }

    init {
        onStart()
    }


    private fun onStart() {
        viewModelScope.launch(mainContext) {
            withContext(ioContext) {
                getDetailsItemUseCase(itemId)
                    .onStart {
                        updateState {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                    .catch { throwable ->
                        updateState { state ->
                            state.copy(
                                isLoading = false,
                                error = throwable
                            )
                        }
                    }.onCompletion {
                        updateState {
                            it.copy(
                                isLoading = false,
                                error = null
                            )
                        }
                    }.single()
            }.onSuccess { savedItem ->
                if (savedItem != null)
                    updateState {
                        it.copy(curProductItem = savedItem, newProductItem = savedItem)
                    }

            }.onError { msg, throwable ->
                updateState {
                    println("Err with message: $msg")
                    it.copy(
                        error = throwable
                    )
                }
            }.onEmpty { msg, _ ->
                updateState {
                    it.copy(
                        error = Throwable("Nothing found with itemId: $itemId!\n$msg")
                    )
                }
            }
        }
    }


    suspend fun saveItem(productItem: ProductItem?): Boolean {
        if (productItem == null) return false
        updateState {
            it.copy(
                isLoading = true
            )
        }
        val result = withContext(ioContext) {
            saveItemUseCase(productItem)
        }
        if (result) {
            updateState {
                it.copy(
                    isLoading = false,
                    curProductItem = productItem,
                    newProductItem = productItem,
                    hasChanges = false
                )
            }
        } else {
            updateState {
                it.copy(
                    isLoading = false,
                    hasChanges = true
                )
            }
        }
        return result
    }

    @OptIn(FlowPreview::class)
    fun onNameChanged(name: String) {
        viewModelScope.launch(mainContext) {
            flowOf(name).debounce(300)
                .distinctUntilChanged()
                .collect { text ->
                    val hasNameError = name.length < 2 || name.isBlank() || name.isEmpty()
                    val hasChanges =
                        text != (currentState.curProductItem?.name ?: "") && !hasNameError
                    val newProduct = currentState.newProductItem?.copy(
                        name = text
                    )
                    // не делаю проверку на hasChanges, потому что, если изменений нет, то не обновится наименование до того, которое было
                    updateState { state ->
                        state.copy(
                            newProductItem = newProduct,
                            hasChanges = hasChanges,
                            hasNameError = hasNameError
                        )
                    }
                }
        }
    }

    @OptIn(FlowPreview::class)
    fun onPriceChanged(price: String) {
        viewModelScope.launch(mainContext) {
            flowOf(price).debounce(300)
                .distinctUntilChanged()
                .collect { text ->
                    val intPrice = try {
                        if(price.length > Int.MAX_VALUE.toString().length || price.isBlank() || price.isEmpty())
                            -1
                        else
                            text.toInt()
                    } catch (ex: Exception) {
                        -1
                    }
                    if(intPrice < 0){
                        updateState {
                            it.copy(
                                hasPriceError = true
                            )
                        }
                    } else {
                        val hasChanges = intPrice != (currentState.curProductItem?.price ?: 0)
                        val newProduct = currentState.newProductItem?.copy(
                            price = intPrice
                        )
                        updateState { state ->
                            state.copy(
                                newProductItem = newProduct,
                                hasChanges = hasChanges,
                                hasPriceError = false
                            )
                        }
                    }
                }
        }
    }

    fun onForFreeChecked(isForFree: Boolean) {
        val hasChanges = isForFree != (currentState.curProductItem?.isForFree ?: false)
        val newProduct = currentState.newProductItem?.copy(
            isForFree = isForFree
        )
        updateState { state ->
            state.copy(
                newProductItem = newProduct,
                hasChanges = hasChanges,
            )
        }
    }

    fun onCoffeeTypeChanged(newCoffeeType: CoffeeType) {
        val hasChanges = newCoffeeType != (currentState.curProductItem?.coffeeType ?: CoffeeType.CAPPUCCINO)
        val newProduct = currentState.newProductItem?.copy(
            coffeeType = newCoffeeType
        )
        updateState { state ->
            state.copy(
                newProductItem = newProduct,
                hasChanges = hasChanges,
            )
        }
    }
}