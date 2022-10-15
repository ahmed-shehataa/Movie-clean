package com.ashehata.movieclean.data.repo

import com.ashehata.movieclean.data.models.MoviesRemoteResponse
import com.ashehata.movieclean.domain.repo.MoviesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test


class MoviesRepositoryImplTest {

    private lateinit var remoteData: FakeRemoteData
    private lateinit var localData: FakeLocalData
    private lateinit var moviesRepository: MoviesRepository
    private var popularMovies = listOf(
        MoviesRemoteResponse.Movie(
            movieId = 10,
            title = "ahmed",
            overview = "overview"
        ),
        MoviesRemoteResponse.Movie(
            movieId = 12,
            title = "ali",
            overview = "overview"
        ),
        MoviesRemoteResponse.Movie(
            movieId = 13,
            title = "mo",
            overview = "overview"
        )
    )

    private var topRatedMovies = listOf(
        MoviesRemoteResponse.Movie(
            movieId = 10,
            title = "ahmed",
            overview = "overview"
        ),
        MoviesRemoteResponse.Movie(
            movieId = 12,
            title = "ali",
            overview = "overview"
        ),
        MoviesRemoteResponse.Movie(
            movieId = 13,
            title = "mo",
            overview = "overview"
        )
    )


    @Before
    fun setup() {
        remoteData = FakeRemoteData()
        localData = FakeLocalData()
        moviesRepository = FakeMoviesRepositoryTest(localData, remoteData)

        remoteData.insertPopularMovies(popularMovies.toList())
        remoteData.insertTopRatedMovies(topRatedMovies.toList())

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getPopularMovies()`() {
       /* runTest {
            val mPopularMovies = moviesRepository.getPopularMovies().flow.first().
            assertThat(mPopularMovies).isEqualTo(popularMovies)
        }*/
    }

    @Test
    fun `getTopRatedMovies()`() {

    }

}