package com.ashehata.movieclean.presentaion.util

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import java.math.RoundingMode
import java.text.DecimalFormat

enum class RateType {
    LOW,
    MEDIUM,
    HIGH,
    UNDEFINED
}

@Composable
fun Double.toRateColor(): Color {
    return when (this.toRate()) {
        RateType.LOW -> Color.Red
        RateType.MEDIUM -> MaterialTheme.colors.primaryVariant
        RateType.HIGH -> MaterialTheme.colors.primary
        RateType.UNDEFINED -> Color.Black
    }
}

fun Double.toRate(): RateType {
    val rate = this.roundFirstDigit()
    return when {
        rate < 5.0 -> RateType.LOW
        rate in 5.0..7.4 -> RateType.MEDIUM
        rate in 7.5..10.0 -> RateType.HIGH
        else -> RateType.UNDEFINED
    }
}

fun Double.roundFirstDigit(): Double {
    return String.format("%.1f", this).toDouble()
}