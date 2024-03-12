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
) : BaseViewModel<List<Ticker>, UiState<List<Ticker>>, TickerListUiAction, Nothing>() {

    init {
        submitAction(TickerListUiAction.GetTickerList)
        submitAction(TickerListUiAction.Load)
    }

    override fun initState(): UiState<List<Ticker>> = UiState.Loading

    override fun handleAction(action: TickerListUiAction) {
        when (action) {
            is TickerListUiAction.Load -> {
                loadTickerList()
            }

            is TickerListUiAction.GetTickerList -> {
                getTickerList()
            }
        }
    }

    private fun getTickerList() {
        viewModelScope.launch {
            marketplaceRepository.tickerList().collect {
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
