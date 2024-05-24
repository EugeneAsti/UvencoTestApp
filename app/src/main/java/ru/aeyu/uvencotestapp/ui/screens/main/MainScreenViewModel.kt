package ru.aeyu.uvencotestapp.ui.screens.main

import android.app.Application
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.aeyu.uvencotestapp.domain.GetRemoteDataUseCase
import ru.aeyu.uvencotestapp.domain.SaveItemUseCase
import ru.aeyu.uvencotestapp.domain.models.ProductItem
import ru.aeyu.uvencotestapp.ui.base.BaseViewModel
import ru.aeyu.uvencotestapp.ui.screens.main.models.MainScreenState
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getRemoteDataUseCase: GetRemoteDataUseCase,
    private val saveItemUseCase: SaveItemUseCase,
    app: Application
) : BaseViewModel<MainScreenState>(app) {

    override fun processCoroutineErrors(throwable: Throwable) {
        println("!!!###!!! err in coroutine MainScreenViewModel")
        throwable.printStackTrace()
        updateState {
            it.copy(
                isLoading = false,
                error = throwable
            )
        }
    }

    override fun setInitialState(): MainScreenState = MainScreenState()
    override fun clearError() {
        updateState {
            it.copy(error = null)
        }
    }

    fun onStart() {
        viewModelScope.launch(mainContext) {
            val list = withContext(ioContext) {
                getRemoteDataUseCase()
                    .onStart {
                        updateState {
                            it.copy(
                                isLoading = true,
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
            }
            updateState {
                it.copy(products = list)
            }
        }
    }


    fun onItemClicked(productItem: ProductItem) {
        viewModelScope.launch(ioContext) {
            saveItemUseCase(productItem)
        }
    }
}