package com.ashehata.movieclean.presentaion.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashehata.movieclean.domain.models.Movie
import com.ashehata.movieclean.domain.useCase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel(){

    private val _moviesList = MutableLiveData<List<Movie>>()
    val moviesList : LiveData<List<Movie>> = _moviesList

    init {

    }

    suspend fun getMovies() {
        viewModelScope.launch {
            val moviesResult = movieUseCase.getMostPopularMovies()
            _moviesList.value = moviesResult
        }

    }
}