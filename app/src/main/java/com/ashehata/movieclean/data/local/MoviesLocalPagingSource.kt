package com.ashehata.movieclean.data.local

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ashehata.movieclean.Logger
import com.ashehata.movieclean.data.models.MovieLocal
import com.ashehata.movieclean.data.util.INITIAL_PAGE
import com.ashehata.movieclean.data.util.PAGE_SIZE_PAGING_LOCAL_MOVIE
import java.io.IOException

const val TAG = "MoviesPagingSource"

class MoviesLocalPagingSource(
    private val localData: LocalData,
    private val firstPage: Int = INITIAL_PAGE,
) : PagingSource<Int, MovieLocal>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieLocal> {
        return try {
            val currentPage = params.key ?: firstPage

            Logger.i(TAG, "current_movie_page:: $currentPage")
            val moviesList = localData.getMovies(
                params.loadSize,
                (currentPage - 1) * params.loadSize
            )

            val nextPage: Int? =
                if (moviesList.isEmpty()) null else currentPage.plus(1)

            if (moviesList.isEmpty()) {
                throw IOException()
            }

            LoadResult.Page(
                data = moviesList,
                prevKey = if (currentPage == INITIAL_PAGE) null else currentPage,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieLocal>): Int {
        return 0
    }

}