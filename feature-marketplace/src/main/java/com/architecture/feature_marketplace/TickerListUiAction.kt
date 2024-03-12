package com.architecture.feature_marketplace

import com.architecture.core.state.UiAction

sealed class TickerListUiAction: UiAction {

    object Load : TickerListUiAction()

    object GetTickerList : TickerListUiAction()

}
