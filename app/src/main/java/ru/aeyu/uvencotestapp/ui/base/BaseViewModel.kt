package ru.aeyu.uvencotestapp.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.aeyu.uvencotestapp.domain.models.TemperatureAndTime
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.random.Random


abstract class BaseViewModel<State : UiState>(
    private val app: Application
) : AndroidViewModel(application = app) {

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            processCoroutineErrors(throwable)
        }

    protected val mainContext = Dispatchers.Main + coroutineExceptionHandler
    protected val ioContext = Dispatchers.IO + coroutineExceptionHandler

    protected abstract fun processCoroutineErrors(throwable: Throwable)

    private var flowWorking = true

    protected fun getStringById(resId: Int): String {
        return app.applicationContext.getString(resId)
    }

    private val initialState: State by lazy { setInitialState() }
    protected abstract fun setInitialState(): State

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    protected val currentState: State get() = _uiState.asStateFlow().value

    protected fun updateState(function: (oldState: State) -> State) {
        _uiState.update(function)
    }

    private val _currentTimeFlow: MutableSharedFlow<String> =
        MutableSharedFlow(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val currentTimeFlow: SharedFlow<String> get() = _currentTimeFlow.asSharedFlow()

    private val _currentTemperatureFlow: MutableSharedFlow<String> =
        MutableSharedFlow(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val currentTemperatureFlow: SharedFlow<String> get() = _currentTemperatureFlow.asSharedFlow()


    private suspend fun startReceiveTemperatureAndTime() {
        flow {
            while (flowWorking) {
                val curTemp = Random.nextDouble(84.9, 96.0)
                val result = String.format(Locale.getDefault(), "%, .1f", curTemp)
                emit(result)
                delay(1000)
            }
        }.combine(timeFlow()){ temperature, time ->
            TemperatureAndTime(temperature, time)
        }.distinctUntilChanged{ old, new ->
            old.temperature == new.temperature && old.time == new.time
        }.catch { throwable ->
            println("!!!###!!! ------------ Error in startReceiveTemperatureAndTime")
            throwable.printStackTrace()
        }.collect { result ->
            println("!!!###!!! ------------ temperature working. Cur.curTemp: $result")
            _currentTimeFlow.emit(result.time)
            _currentTemperatureFlow.emit(result.temperature)
        }
    }

    private fun timeFlow(): Flow<String> = flow {
        while (flowWorking) {
            val calendar: Calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val curTime = dateFormat.format(calendar.time)
            println("!!!###!!! ------------ timer working. Cur.time: $curTime")
            emit(curTime)
            delay(60000)
        }
    }

    override fun onCleared() {
        onRemoveObserver()
        super.onCleared()
    }

    open fun onRemoveObserver() {
        flowWorking = false
    }

    open fun onAddObserver() {
        flowWorking = true
        viewModelScope.launch(mainContext) {
            startReceiveTemperatureAndTime()
        }
    }

    abstract fun clearError()
}