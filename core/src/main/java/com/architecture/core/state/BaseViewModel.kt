package com.architecture.core.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<S : BaseState, A : UiAction> : ViewModel() {

    private val _uiStateFlow: MutableStateFlow<S> by lazy {
        MutableStateFlow(initState())
    }
    val uiStateFlow: StateFlow<S> = _uiStateFlow.asStateFlow()
    private val actionFlow: MutableSharedFlow<A> = MutableSharedFlow()

    init {
        viewModelScope.launch {
            actionFlow.handleAction().collect {}
        }
    }

    abstract fun initState(): S

    abstract fun Flow<A>.handleAction(): Flow<Unit>

    fun submitAction(action: A) {
        viewModelScope.launch {
            actionFlow.emit(action)
        }
    }

    fun submitState(state: S) = _uiStateFlow.update { state }

}
