package com.architecture.feature_marketplace

import androidx.lifecycle.viewModelScope
import com.architecture.core.repository.MarketplaceRepository
import com.architecture.core.state.BaseViewModel
import com.architecture.core.state.UiState
import com.architecture.core.util.NetworkConnectivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MarketplaceViewModel @Inject constructor(
    private val marketplaceRepository: MarketplaceRepository,
    private val networkConnectivity: NetworkConnectivity,
) : BaseViewModel<MarketplaceUiState, MarketplaceUiAction>() {

    init {
        submitAction(MarketplaceUiAction.Search)
        observeForConnectivityChanges()
    }

    override fun initState(): MarketplaceUiState = MarketplaceUiState()

    override fun Flow<MarketplaceUiAction>.handleAction(): Flow<Unit> {
        val search = filterIsInstance<MarketplaceUiAction.Search>()
            .flatMapLatest {
                marketplaceRepository.tickerList(uiStateFlow.value.query)
                    .map { tickerList ->
                        submitState(uiStateFlow.value.copy(tickers = UiState.Success(tickerList)))
                    }
            }

        return search
    }

    fun pollingData() {
        viewModelScope.launch {
            marketplaceRepository.loadTickerList()
        }
    }

    fun searchTicker(query: String) {
        submitState(uiStateFlow.value.copy(query = query))
        submitAction(MarketplaceUiAction.Search)
    }

    private fun observeForConnectivityChanges() {
        networkConnectivity.observeConnectivityChanges()
            .map(Boolean::not)
            .onEach {
                submitState(uiStateFlow.value.copy(isOffline = it))
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = false,
            ).launchIn(viewModelScope)
    }

}
