package com.ashehata.movieclean.data.paging

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.ashehata.movieclean.data.local.MoviesDao
import com.ashehata.movieclean.data.models.MovieLocal
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MoviesLocalPagingSourceTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var moviesDao: MoviesDao

    private lateinit var moviesLocalPagingSource: MoviesLocalPagingSource

    companion object {
        val moviesList: List<MovieLocal> = (1..20).map {
            return@map MovieLocal(
                id = it,
                movieId = it + 100,
                name = "aa",
                imageUrlFull = "",
                imageUrlSmall = "",
                description = "des",
                voteAverage = 4.0,
                overview = "des",
                releaseDate = ""
            )
        }

    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        moviesLocalPagingSource = MoviesLocalPagingSource(moviesDao, firstPage = 1)
    }


    @Test
    fun `load first page from cached popular movies from local DB - success`() = runBlockingTest {
        val limit = 20
        val offset = 0

        given(moviesDao.getMovies(limit, offset)).willReturn(moviesList)

        val result = moviesLocalPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = limit,
                placeholdersEnabled = false
            )
        )

        val expected = PagingSource.LoadResult.Page(
            data = moviesList,
            prevKey = null,
            nextKey = 2
        )

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `load second page from cached popular movies from local DB - success`() = runBlockingTest {
        val limit = 20
        val offset = 1 * 20

        given(moviesDao.getMovies(limit, offset)).willReturn(moviesList)

        val result = moviesLocalPagingSource.load(
            PagingSource.LoadParams.Append(
                key = 2,
                loadSize = limit,
                placeholdersEnabled = false
            )
        )

        val expected = PagingSource.LoadResult.Page(
            data = moviesList,
            prevKey = 2,
            nextKey = 3
        )

        assertThat(result).isEqualTo(expected)
    }

}