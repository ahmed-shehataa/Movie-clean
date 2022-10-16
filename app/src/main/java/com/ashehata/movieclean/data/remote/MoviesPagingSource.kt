package com.ashehata.movieclean.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ashehata.movieclean.Logger
import com.ashehata.movieclean.data.local.LocalData
import com.ashehata.movieclean.data.mappers.toLocalMovie
import com.ashehata.movieclean.data.models.MoviesRemoteResponse
import com.ashehata.movieclean.data.util.INITIAL_PAGE
import com.ashehata.movieclean.data.util.cachedPages

const val TAG = "MoviesPagingSource"

enum class MoviesType {
    POPULAR,
    TOP_RATED,
    NONE
}

class MoviesPagingSource(
    private val remoteData: RemoteData,
    private val localData: LocalData,
    private val firstPage: Int = INITIAL_PAGE,
    private val moviesType: MoviesType = MoviesType.NONE,
    private val forceCashing: Boolean = false,
    private val cachingPagesNum: List<Int> = cachedPages,
) : PagingSource<Int, MoviesRemoteResponse.Movie>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesRemoteResponse.Movie> {
        return try {
            val currentPage = params.key ?: firstPage

            Logger.i(TAG, "current_movie_page:: $currentPage")
            val moviesList = when (moviesType) {
                MoviesType.POPULAR -> remoteData.getPopularMovies(currentPage).movies
                MoviesType.TOP_RATED -> remoteData.getTopRatedMovies(currentPage).movies
                MoviesType.NONE -> {
                    throw IllegalArgumentException(
                        "Parameter MoviesType Not passed",
                        Throwable("Please select movies type to get")
                    )
                }
            } ?: emptyList()

            if (forceCashing) {
                if (moviesList.isNotEmpty() && currentPage in cachingPagesNum) {
                    val localMovies = moviesList.map {
                        it.toLocalMovie()
                    }
                    localData.insertMovies(localMovies)
                }
            }

            val nextPage: Int? =
                if (moviesList.isEmpty()) null else currentPage.plus(1)

            LoadResult.Page(
                data = moviesList,
                prevKey = if (currentPage == INITIAL_PAGE) null else currentPage,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MoviesRemoteResponse.Movie>): Int {
        return 0
    }

}