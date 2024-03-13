package com.architecture.feature_marketplace

import androidx.lifecycle.viewModelScope
import com.architecture.core.model.Ticker
import com.architecture.core.repository.MarketplaceRepository
import com.architecture.core.state.BaseViewModel
import com.architecture.core.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MarketplaceViewModel @Inject constructor(
    private val marketplaceRepository: MarketplaceRepository,
) : BaseViewModel<List<Ticker>, UiState<List<Ticker>>, MarketplaceUiAction, Nothing>() {

    init {
        submitAction(MarketplaceUiAction.Search(""))
        submitAction(MarketplaceUiAction.Load)
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
