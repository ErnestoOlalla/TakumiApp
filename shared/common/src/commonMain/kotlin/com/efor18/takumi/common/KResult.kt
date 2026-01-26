@file:Suppress("unused")

package com.efor18.takumi.common


sealed class KResult<out T> {
    data class Success<T>(val value: T) : KResult<T>()
    data class Error(val error: KError) : KResult<Nothing>()

    inline val isSuccess: Boolean
        get() = this is Success

    inline val isError: Boolean
        get() = this is Error

    fun value(): T? {
        return (this as? Success)?.value
    }

    fun error(): KError? {
        return (this as? Error)?.error
    }

    inline fun <T, K> KResult<T>.map(transform: (T) -> K): KResult<K> {
        return when (this) {
            is Success -> success(transform(value))
            is Error -> error(error)
        }
    }

    companion object {
        fun <T> success(value: T): KResult<T> = Success(value)

        fun error(type: KErrorType, message: String, error: Throwable): KResult<Nothing> =
            Error(KError(type, message, error))

        fun error(error: KError): KResult<Nothing> = Error(error)

        fun unknownError(): KResult<Nothing> = Error(ERROR_UNKNOWN)
    }
}

inline fun <T, R> T.runKatching(block: T.() -> R): KResult<R> {
    return try {
        KResult.success(block())
    } catch (e: Throwable) {
        KResult.error(ERROR_UNKNOWN)
    }
}

data class KError(val type: KErrorType, val message: String, val throwable: Throwable? = null)

enum class KErrorType {
    UNKNOWN,
    NO_NETWORK
}

val ERROR_UNKNOWN = KError(KErrorType.UNKNOWN, "Unknown Error")