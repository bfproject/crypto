package com.architecture.feature_marketplace

import androidx.lifecycle.viewModelScope
import com.architecture.core.model.Ticker
import com.architecture.core.repository.MarketplaceRepository
import com.architecture.core.state.BaseViewModel
import com.architecture.core.state.UiState
import com.architecture.core.util.NetworkConnectivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class MarketplaceViewModel @Inject constructor(
    private val marketplaceRepository: MarketplaceRepository,
    private val networkConnectivity: NetworkConnectivity,
) : BaseViewModel<List<Ticker>, UiState<List<Ticker>>, MarketplaceUiAction, Nothing>() {

    val isOffline = networkConnectivity.observeConnectivityChanges().map(Boolean::not).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    init {
        submitAction(MarketplaceUiAction.Search(""))
    }

    override fun initState(): UiState<List<Ticker>> = UiState.Loading

    override fun handleAction(action: MarketplaceUiAction) {
        when (action) {
            is MarketplaceUiAction.Load -> {
                loadTickerList()
            }

            is MarketplaceUiAction.Search -> {
                search(action.query)
            }
        }
    }

    private fun search(query: String) {
        viewModelScope.launch {
            marketplaceRepository.tickerList(query).collect {
                submitState(UiState.Success(it))
            }
        }
    }

    private fun loadTickerList() {
        viewModelScope.launch {
            marketplaceRepository.loadTickerList()
        }
    }

}
