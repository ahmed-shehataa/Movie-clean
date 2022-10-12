package com.ashehata.movieclean.presentaion.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ashehata.movieclean.domain.models.Movie
import com.ashehata.movieclean.domain.useCase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel(){

    private val _moviesFlow = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val moviesList : StateFlow<PagingData<Movie>> = _moviesFlow

    init {
        getMovies()
    }

    fun getMovies() {
        viewModelScope.launch {
            val moviesResult = movieUseCase.getMostPopularMovies().cachedIn(viewModelScope)
            _moviesFlow.emit(moviesResult.first())
        }

    }
}