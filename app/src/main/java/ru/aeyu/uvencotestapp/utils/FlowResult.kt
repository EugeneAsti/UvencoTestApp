package ru.aeyu.uvencotestapp.utils


class FlowResult<T> private constructor(
    val dataStatus: DataStatus,
    val data: T? = null,
    val message: String = "",
    val throwable: Throwable? = null,
) {

    val isSuccess: Boolean = dataStatus == DataStatus.SUCCESS

    companion object {
        fun <T> completeSuccess(data: T? = null): FlowResult<T> {
            return FlowResult(DataStatus.SUCCESS, data)
        }

        fun <T> error(someMessage: String = "", throwable: Throwable? = null): FlowResult<T> {
            return FlowResult(
                dataStatus = DataStatus.ERROR,
                data = null,
                message = someMessage,
                throwable = throwable
            )
        }

        fun <T> completeEmpty(
            data: T? = null,
            messageResult: String = ""
        ): FlowResult<T> {
            return FlowResult(
                dataStatus = DataStatus.EMPTY,
                data = data,
                message = messageResult
            )
        }
    }
}

inline fun <T> FlowResult<T>.onSuccess(action: (value: T?) -> Unit): FlowResult<T> {
    if (this.dataStatus == DataStatus.SUCCESS)
        action(this.data)
    return this
}

inline fun <T> FlowResult<T>.onError(action: (msg: String, throwable: Throwable?) -> Unit): FlowResult<T> {

    if (this.dataStatus == DataStatus.ERROR)
        action(this.message, this.throwable)
    return this
}

inline fun <T> FlowResult<T>.onEmpty(action: (msg: String, data: T?) -> Unit): FlowResult<T> {

    if (this.dataStatus == DataStatus.EMPTY)
        action(this.message, this.data)
    return this
}

enum class DataStatus {
    SUCCESS,
    ERROR,
    EMPTY,
}