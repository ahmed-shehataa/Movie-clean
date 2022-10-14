package com.ashehata.movieclean.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ashehata.movieclean.util.Logger
import com.ashehata.movieclean.data.models.MoviesRemoteResponse

const val INITIAL_PAGE = 1
const val PAGE_SIZE_PAGING_EXPLORE = 10
const val TAG = "MoviesPagingSource"


class MoviesPagingSource(
    private val methodCall : suspend (Int) -> MoviesRemoteResponse,
    private val firstPage: Int = INITIAL_PAGE,
) : PagingSource<Int, MoviesRemoteResponse.Movie>() {


    override suspend fun load(params: LoadParams<Int>): PagingSource.LoadResult<Int, MoviesRemoteResponse.Movie> {
        return try {
            val currentPage = params.key ?: firstPage

            Logger.i(TAG, "current_movie_page:: $currentPage")
            val moviesList = methodCall.invoke(currentPage).movies ?: emptyList()

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