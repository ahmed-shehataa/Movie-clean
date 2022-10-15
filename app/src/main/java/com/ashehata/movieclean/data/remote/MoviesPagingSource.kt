package com.ashehata.movieclean.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ashehata.movieclean.Logger
import com.ashehata.movieclean.data.models.MoviesRemoteResponse
import com.ashehata.movieclean.data.util.INITIAL_PAGE
import com.ashehata.movieclean.data.util.cachedPages

const val TAG = "MoviesPagingSource"


class MoviesPagingSource(
    private val apiCall: suspend (Int) -> MoviesRemoteResponse,
    private val localCall: suspend (List<MoviesRemoteResponse.Movie>) -> Unit = {},
    private val firstPage: Int = INITIAL_PAGE,
) : PagingSource<Int, MoviesRemoteResponse.Movie>() {


    override suspend fun load(params: LoadParams<Int>): PagingSource.LoadResult<Int, MoviesRemoteResponse.Movie> {
        return try {
            val currentPage = params.key ?: firstPage

            Logger.i(TAG, "current_movie_page:: $currentPage")
            val moviesList = apiCall.invoke(currentPage).movies ?: emptyList()
            if (moviesList.isNotEmpty() && currentPage in cachedPages) {
                localCall(moviesList)
            }

            Logger.i(TAG, "moviesList:: " + moviesList.size.toString())

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