package com.architecture.core.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 *  Observes network connectivity changes.
 *
 *  This class emits values indicating whether the network is available or lost.
 *  It also performs an initial check on network connectivity during construction.
 */
class NetworkConnectivity @Inject constructor(@ApplicationContext private val context: Context) {
    fun observeConnectivityChanges(): Flow<Boolean> = callbackFlow {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(true) // Network is available
            }

            override fun onLost(network: Network) {
                trySend(false) // Network is lost
            }
        }

        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build(),
            networkCallback
        )

        trySend(connectivityManager.isCurrentlyConnected())

        // Cleanup when the flow is cancelled
        awaitClose {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }

    // Checks if the network is currently connected.
    private fun ConnectivityManager?.isCurrentlyConnected() = when (this) {
        null -> false
        else -> {
            activeNetwork
                ?.let(::getNetworkCapabilities)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                ?: false
        }
    }
}
