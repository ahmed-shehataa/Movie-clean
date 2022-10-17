package com.ashehata.movieclean.domain.useCase

import androidx.paging.Pager
import androidx.paging.PagingData
import com.ashehata.movieclean.domain.models.Movie
import kotlinx.coroutines.flow.Flow


interface MovieUseCase {

    suspend fun getTopRatedMovies(): Flow<PagingData<Movie>>

    suspend fun getMostPopularMovies(): Flow<PagingData<Movie>>
}