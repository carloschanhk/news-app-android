package com.carloscoding.newsapp.utils

sealed class Result<R> {
    data class Success<T>(val value: T): Result<T>()
    data class Error<T>(val exception: Exception): Result<T>()

    fun takeIfSuccess(): R? = (this as? Success)?.value
    fun takeSuccess(): R = (this as Success).value
    fun takeIfError(): Exception? = (this as? Error)?.exception
    fun takeError(): Exception = (this as Error).exception
}
