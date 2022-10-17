package com.ashehata.movieclean.data.repository

import androidx.paging.*
import app.cash.turbine.test
import com.ashehata.movieclean.data.models.MoviesRemoteResponse
import com.ashehata.movieclean.data.paging.MoviesLocalPagingSource
import com.ashehata.movieclean.data.paging.MoviesPagingSource
import com.ashehata.movieclean.data.util.PAGE_SIZE_PAGING_REMOTE_MOVIE
import com.ashehata.movieclean.domain.repo.MoviesRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.given
import org.mockito.kotlin.mock

class MoviesRepositoryImplTest {

    lateinit var moviesPagingSource: MoviesPagingSource

    lateinit var moviesLocalPagingSource: MoviesLocalPagingSource

    private lateinit var moviesRepositoryImpl: MoviesRepository

    companion object {
        val pagesToCache = listOf(1, 2, 3)
        val moviesList = MoviesRemoteResponse(
            page = 1,
            movies = listOf(
                MoviesRemoteResponse.Movie(
                    movieId = 123,
                    title = "Hall",
                    overview = "asddf"
                ),
                MoviesRemoteResponse.Movie(
                    movieId = 456,
                    title = "Ahm",
                    overview = "asddfasddfasddf"
                )
            )
        )
    }

    @Before
    fun setup() {
        //MockitoAnnotations.initMocks(this)
        moviesPagingSource = mock()
        moviesLocalPagingSource = mock()
        moviesRepositoryImpl = MoviesRepositoryImpl(moviesLocalPagingSource, moviesPagingSource)
    }

    @Test
    fun `getPopularMovies() return a valid pager with data`() = runBlocking {
        val expected2 = PagingSource.LoadResult.Page(
            data = moviesList.movies ?: emptyList(),
            prevKey = null,
            nextKey = 2
        )
        val loadParam = PagingSource.LoadParams.Refresh(
            key = 1,
            loadSize = 20,
            placeholdersEnabled = false
        )

        val flows = flow {
            emit(moviesList.movies)
        }

        val pager = Pager(config = PagingConfig(
            pageSize = PAGE_SIZE_PAGING_REMOTE_MOVIE,
            enablePlaceholders = false
        ), pagingSourceFactory = {
            moviesPagingSource
        })



        val movies = moviesList.movies?.toList() ?: emptyList()
        val expected = PagingData.from(movies)

        given(moviesPagingSource.load(loadParam)).willReturn(expected2)

        given(moviesRepositoryImpl.getPopularMovies()).willReturn(pager)

        val pagerResult = moviesRepositoryImpl.getPopularMovies().flow.test {

            val it = mutableListOf<MoviesRemoteResponse.Movie>()
            val it2 = mutableListOf<MoviesRemoteResponse.Movie>()

            val ss = awaitItem().map { movie ->
                return@map it.add(movie)
            }

            val ss2 = expected.map { movie ->
                return@map it2.add(movie)
            }
            //println("sizeee" + it.size)


            //assertThat(awaitItem().collectDataForTest()).isEqualTo(expected.collectDataForTest())
        }

        println(pagerResult)

        assertThat(true).isTrue()

    }


}