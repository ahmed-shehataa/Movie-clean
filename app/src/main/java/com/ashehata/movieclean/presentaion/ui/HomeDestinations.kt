package com.ashehata.movieclean.presentaion.ui

import androidx.compose.runtime.Composable

interface HomeDestination {
    val route: String
    val screen: @Composable () -> Unit
}

object Movies : HomeDestination {
    override val route = "Movies"
    override val screen: @Composable () -> Unit = { }
}

object MovieDetails : HomeDestination {
    override val route = "MovieDetails"
    override val screen: @Composable () -> Unit = { }
}


val homeScreens = listOf(Movies, MovieDetails)
