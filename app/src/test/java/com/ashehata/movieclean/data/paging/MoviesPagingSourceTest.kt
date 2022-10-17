package com.ashehata.movieclean.data.paging

import androidx.paging.PagingSource
import com.ashehata.movieclean.data.local.MoviesDao
import com.ashehata.movieclean.data.models.MoviesRemoteResponse
import com.ashehata.movieclean.data.remote.RetrofitApi
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.*

class MoviesPagingSourceTest {

    @Mock
    lateinit var retrofitApi: RetrofitApi

    @Mock
    lateinit var moviesDao: MoviesDao

    lateinit var moviesPagingSource: MoviesPagingSource


    companion object {
        val pagesToCache = listOf(1, 2, 3)
        val moviesRemoteResponse = MoviesRemoteResponse(
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
        val nextMoviesRemoteResponse = MoviesRemoteResponse(
            page = 2,
            movies = listOf(
                MoviesRemoteResponse.Movie(
                    movieId = 101,
                    title = "qqq",
                    overview = "vbvbvbvb"
                ),
                MoviesRemoteResponse.Movie(
                    movieId = 102,
                    title = "xxx",
                    overview = "xzxzxzx"
                )
            )
        )
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `load popular movies from remote paging source - failure - http error`() = runBlocking {
        moviesPagingSource = MoviesPagingSource(
            retrofitApi, moviesDao,
            moviesType = MoviesType.POPULAR,
            forceCashing = false
        )

        val error = RuntimeException("404", Throwable())

        given(retrofitApi.getPopularMovies(any())).willThrow(error)


        val expected = PagingSource.LoadResult.Error<Int, MoviesRemoteResponse.Movie>(error)

        val result = moviesPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `refresh popular movies from remote paging source - success`() = runBlocking {
        moviesPagingSource = MoviesPagingSource(
            retrofitApi, moviesDao,
            moviesType = MoviesType.POPULAR,
            forceCashing = false
        )


        given(retrofitApi.getPopularMovies(any())).willReturn(moviesRemoteResponse)

        val expectedResult = PagingSource.LoadResult.Page(
            data = moviesRemoteResponse.movies ?: emptyList(),
            prevKey = null,
            nextKey = 2
        )

        val result = moviesPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )
        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `append popular movies from remote paging source - success`() = runBlocking {
        moviesPagingSource = MoviesPagingSource(
            retrofitApi, moviesDao,
            moviesType = MoviesType.POPULAR,
            forceCashing = false
        )


        given(retrofitApi.getPopularMovies(any())).willReturn(nextMoviesRemoteResponse)

        val expectedResult = PagingSource.LoadResult.Page(
            data = nextMoviesRemoteResponse.movies ?: emptyList(),
            prevKey = 2,
            nextKey = 3
        )

        val result = moviesPagingSource.load(
            PagingSource.LoadParams.Append(
                key = 2,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )
        assertThat(result).isEqualTo(expectedResult)
    }


    @Test
    fun `load top rated movies from remote paging source - failure - http error`() =
        runBlocking {
            moviesPagingSource = MoviesPagingSource(
                retrofitApi, moviesDao,
                moviesType = MoviesType.TOP_RATED,
                forceCashing = false
            )

            val error = RuntimeException("404", Throwable())

            given(retrofitApi.getTopRatedMovies(any())).willThrow(error)


            val expected = PagingSource.LoadResult.Error<Int, MoviesRemoteResponse.Movie>(error)

            val result = moviesPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 10,
                    placeholdersEnabled = false
                )
            )
            assertThat(result).isEqualTo(expected)
        }

    @Test
    fun `refresh top rated movies from remote paging source - success`() = runBlocking {
        moviesPagingSource = MoviesPagingSource(
            retrofitApi, moviesDao,
            moviesType = MoviesType.TOP_RATED,
            forceCashing = false
        )


        given(retrofitApi.getTopRatedMovies(any())).willReturn(moviesRemoteResponse)

        val expectedResult = PagingSource.LoadResult.Page(
            data = moviesRemoteResponse.movies ?: emptyList(),
            prevKey = null,
            nextKey = 2
        )

        val result = moviesPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )
        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `append top rated movies from remote paging source - success`() = runBlocking {
        moviesPagingSource = MoviesPagingSource(
            retrofitApi, moviesDao,
            moviesType = MoviesType.TOP_RATED,
            forceCashing = false
        )


        given(retrofitApi.getTopRatedMovies(any())).willReturn(nextMoviesRemoteResponse)

        val expectedResult = PagingSource.LoadResult.Page(
            data = nextMoviesRemoteResponse.movies ?: emptyList(),
            prevKey = 2,
            nextKey = 3
        )

        val result = moviesPagingSource.load(
            PagingSource.LoadParams.Append(
                key = 2,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )
        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `load and enable cache popular movies- success`() = runBlocking {
        moviesPagingSource = MoviesPagingSource(
            retrofitApi, moviesDao,
            moviesType = MoviesType.POPULAR,
            forceCashing = true,
            cachingPagesNum = pagesToCache
        )

        given(retrofitApi.getPopularMovies(any())).willReturn(moviesRemoteResponse)

        moviesPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = pagesToCache.random(),
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        verify(moviesDao, times(1)).insertMovies(any())
    }

    @Test
    fun `load and disable cache popular movies- success`() = runBlocking {
        moviesPagingSource = MoviesPagingSource(
            retrofitApi, moviesDao,
            moviesType = MoviesType.POPULAR,
            forceCashing = false,
            cachingPagesNum = pagesToCache
        )

        given(retrofitApi.getPopularMovies(any())).willReturn(moviesRemoteResponse)

        moviesPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = pagesToCache.random(),
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        verify(moviesDao, never()).insertMovies(any())
    }

    @Test
    fun `pass invalid movie type(NONE) - failed`() = runBlocking {
        moviesPagingSource = MoviesPagingSource(
            retrofitApi, moviesDao,
            moviesType = MoviesType.NONE,
            forceCashing = false,
        )
        val error = IllegalArgumentException(
            "Parameter MoviesType Not passed",
            Throwable("Please select movies type to get")
        )

        given(retrofitApi.getPopularMovies(any())).willThrow(error)

        val expected = PagingSource.LoadResult.Error<Int, MoviesRemoteResponse.Movie>(error)

        val pagingResult = moviesPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = pagesToCache.random(),
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        val result = when(pagingResult) {
            is PagingSource.LoadResult.Error -> pagingResult.throwable
            is PagingSource.LoadResult.Invalid -> null
            is PagingSource.LoadResult.Page -> null
        }

        assertThat(result).isNotEqualTo(expected.throwable)
    }

}