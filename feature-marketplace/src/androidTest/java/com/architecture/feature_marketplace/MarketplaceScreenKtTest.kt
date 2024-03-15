package com.architecture.feature_marketplace

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.architecture.core.model.Ticker
import com.architecture.core.state.UiState
import org.junit.Rule
import org.junit.Test

class MarketplaceScreenKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysMarketplaceMainViewUserList() {
        with(composeTestRule) {
            setContent {
                MarketplaceMainView(state = MarketplaceUiState(tickers = UiState.Success(listOf(testTickerItem1)))) {}
            }
            onNodeWithText("BORG").assertIsDisplayed()
            onNodeWithText("/USD").assertIsDisplayed()
            onNodeWithText("+50.00%").assertIsDisplayed()
            onNodeWithText("$0.45").assertIsDisplayed()
        }
    }

    @Test
    fun displaysMarketplaceMainViewLoading() {
        with(composeTestRule) {
            setContent { MarketplaceMainView(state = MarketplaceUiState(tickers = uiStateLoading)) {} }
            onNodeWithTag("CircularProgressIndicator").assertIsDisplayed()
        }
    }

    @Test
    fun displaysMarketplaceMainViewError() {
        with(composeTestRule) {
            setContent { MarketplaceMainView(MarketplaceUiState(tickers = uiStateError)) {} }
            onNodeWithTag("CustomEmptyOrErrorStateImage").assertIsDisplayed()
        }
    }
}

private val testTickerItem1 = Ticker("tBORGUSD", 0.5, 0.45)
private val uiStateLoading = UiState.Loading
private val uiStateError = UiState.Error.Generic("")
