package com.architecture.core.state

fun DataState.Error.asUiState(): UiState.Error {
    return when (this) {
        is DataState.Error.NoConnection -> UiState.Error.NoConnection(this.errorMessage ?: defaultErrorMsg)
        is DataState.Error.Generic -> UiState.Error.Generic(this.errorMessage ?: defaultErrorMsg)
    }
}

const val defaultErrorMsg = "An error occur"
