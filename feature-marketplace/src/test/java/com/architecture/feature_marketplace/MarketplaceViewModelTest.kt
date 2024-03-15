package com.architecture.feature_marketplace

import app.cash.turbine.test
import com.architecture.core.model.Ticker
import com.architecture.core.repository.MarketplaceRepository
import com.architecture.core.state.UiState
import com.architecture.core.util.NetworkConnectivity
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MarketplaceViewModelTest {

    private lateinit var marketplaceRepository: MarketplaceRepository
    private lateinit var networkConnectivity: NetworkConnectivity
    private lateinit var viewModel: MarketplaceViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        marketplaceRepository = mockk(relaxed = true)
        networkConnectivity = mockk(relaxed = true)
        viewModel = MarketplaceViewModel(marketplaceRepository, networkConnectivity)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `WHEN action Search empty is received THEN state contains the full list of tickers`() = runTest {

        val tickerList = listOf(testTickerItem1, testTickerItem2)
        val stateExpected = MarketplaceUiState(tickers = UiState.Success(tickerList))

        // Given
        every { (marketplaceRepository.tickerList("")) }.answers { flowOf(tickerList) }

        viewModel.uiStateFlow.test {
            // When
            viewModel.searchTicker("")

            // Then
            // Assert Base state
            assertEquals(MarketplaceUiState(), awaitItem())

            // Assert Success state with all tickers
            assertEquals(stateExpected, awaitItem())

            // No more items should be emitted
            expectNoEvents()
        }
    }

    @Test
    fun `WHEN action Search is received THEN state contains empty list of tickers`() = runTest {

        val query = "Nonexistent Ticker"
        val stateExpected = MarketplaceUiState(tickers = UiState.Success(emptyList()), query = query)

        // Given
        every { (marketplaceRepository.tickerList(query)) }.answers { flowOf(emptyList()) }

        viewModel.uiStateFlow.test {
            // When
            viewModel.searchTicker(query)

            // Then
            // Assert Base state
            assertEquals(MarketplaceUiState(), awaitItem())

            // Assert query change state
            assertEquals(MarketplaceUiState(query = query), awaitItem())

            // Assert Success state with empty tickers
            assertEquals(stateExpected, awaitItem())

            // No more items should be emitted
            expectNoEvents()
        }
    }

    @Test
    fun `WHEN action Search is received THEN state contains list of match tickers`() = runTest {

        val query = "BORG"
        val stateExpected = MarketplaceUiState(tickers = UiState.Success(listOf(testTickerItem1)), query = query)

        // Given
        every { (marketplaceRepository.tickerList(query)) }.answers { flowOf(listOf(testTickerItem1)) }

        viewModel.uiStateFlow.test {
            // When
            viewModel.searchTicker(query)

            // Then
            // Assert Base state
            assertEquals(MarketplaceUiState(), awaitItem())

            // Assert query change state
            assertEquals(MarketplaceUiState(query = query), awaitItem())

            // Assert Success state with tickers
            assertEquals(stateExpected, awaitItem())

            // No more items should be emitted
            expectNoEvents()
        }
    }

}

private val testTickerItem1 = Ticker("tBORGUSD", 0.5, 0.45)
private val testTickerItem2 = Ticker("tBTCUSD", 0.05, 70000.0)
