package com.architecture.feature_marketplace.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

fun Double.formatPercent(): String {
    val sign = if (this >= 0) "+" else ""
    val percentage = String.format("%.2f", this * 100).plus("%")
    return "$sign$percentage"
}

val Double.percentColor
    @Composable
    get() = if (this >= 0) MaterialTheme.colorScheme.primary else Color.Red
