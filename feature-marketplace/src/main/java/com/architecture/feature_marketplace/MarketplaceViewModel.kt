package com.architecture.feature_marketplace

import androidx.lifecycle.viewModelScope
import com.architecture.core.repository.MarketplaceRepository
import com.architecture.core.state.BaseViewModel
import com.architecture.core.state.MarketplaceUiState
import com.architecture.core.state.UiState
import com.architecture.core.util.NetworkConnectivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MarketplaceViewModel @Inject constructor(
    private val marketplaceRepository: MarketplaceRepository,
    networkConnectivity: NetworkConnectivity,
) : BaseViewModel<MarketplaceUiState, MarketplaceUiAction>() {

    val isOffline = networkConnectivity.observeConnectivityChanges().map(Boolean::not).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = false,
    )

    init {
        submitAction(MarketplaceUiAction.Search(""))
    }

    override fun initState(): MarketplaceUiState = MarketplaceUiState()

    override fun Flow<MarketplaceUiAction>.handleAction(): Flow<Unit> {
        val search = filterIsInstance<MarketplaceUiAction.Search>()
            .flatMapLatest {
                marketplaceRepository.tickerList(it.query)
                    .map { tickerList ->
                        submitState(state.copy(tickers = UiState.Success(tickerList)))

                    }
            }

        return search
    }

    fun pullingData() {
        viewModelScope.launch {
            marketplaceRepository.loadTickerList()
        }
    }

    fun searchTicker(query: String) {
        submitAction(MarketplaceUiAction.Search(query))
    }

}
