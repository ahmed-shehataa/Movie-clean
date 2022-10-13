package com.ashehata.movieclean.presentaion.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.ashehata.movieclean.presentaion.models.MoviesType
import com.ashehata.movieclean.presentaion.util.theme.MovieCleanTheme
import com.ashehata.movieclean.presentaion.viewModel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesActivity : ComponentActivity() {

    private val moviesViewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestMovies(MoviesType.POPULAR)
        setContent {
            MovieCleanTheme {
                StatusBarColor()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val moviesFlow = moviesViewModel.moviesList
                    MoviesScreen(moviesFlow, onFilterClicked = { type ->
                        requestMovies(type)
                    })
                }
            }
        }
    }

    private fun requestMovies(type: MoviesType) {
        lifecycleScope.launchWhenCreated {
            moviesViewModel.moviesType.send(type)
        }
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MovieCleanTheme {
        //MoviesScreen(moviesFlow)
    }
}