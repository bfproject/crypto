package com.architecture.core.state

/**
 * Contains the state of the view
 */
sealed class UiState<out T : Any> {

    object Loading : UiState<Nothing>()

    data class Success<T : Any>(val data: T) : UiState<T>()

    sealed class Error(val errorMsg: String) : UiState<Nothing>() {
        class NoConnection(errorMessage: String) : Error(errorMessage)
        class Http(errorMessage: String) : Error(errorMessage)
        class Generic(errorMessage: String) : Error(errorMessage)
    }

}
