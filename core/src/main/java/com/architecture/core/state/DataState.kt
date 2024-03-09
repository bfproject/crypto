package com.architecture.core.state

/**
 * Contains the data state of the data layer
 */
sealed class DataState<out T : Any> {

    data class Success<out T : Any>(val data: T) : DataState<T>()

    sealed class Error(val errorMessage: String?) : DataState<Nothing>() {
        class NoConnection(errorMessage: String?) : Error(errorMessage)
        class Http(errorMessage: String?) : Error(errorMessage)
        class Generic(errorMessage: String?) : Error(errorMessage)
    }

}
