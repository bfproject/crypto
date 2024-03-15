package com.architecture.feature_marketplace

import com.architecture.core.state.UiAction

sealed class MarketplaceUiAction: UiAction {

    object Load : MarketplaceUiAction()

    object Search : MarketplaceUiAction()

}
