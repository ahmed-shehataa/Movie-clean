package com.ashehata.movieclean.presentaion.util

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


fun Double.isGreaterThan(x: Int) = this > x
fun Double.isSmallerThan(x: Int) = this < x

@Composable
fun Double.ToRateColor(): Color {
    return when {
        this <= 5.0 -> Color.Red
        this in 5.0..7.4 -> MaterialTheme.colors.primaryVariant
        this >= 7.5 -> MaterialTheme.colors.primary
        else -> Color.Black
    }
}