package com.ashehata.movieclean.data.remoteMediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ashehata.movieclean.data.di.AppDatabase
import com.ashehata.movieclean.data.local.dataStore.DataStore
import com.ashehata.movieclean.data.mappers.toLocalMovie
import com.ashehata.movieclean.data.models.MovieLocal
import com.ashehata.movieclean.data.models.RemoteKeys
import com.ashehata.movieclean.data.remote.RemoteData
import com.ashehata.movieclean.util.Logger
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val forceRefresh: Boolean,
    private val appDatabase: AppDatabase,
    private val remoteData: RemoteData,
    private val dataStore: DataStore,
    private val initialPageCount: Int = 1,
) : RemoteMediator<Int, MovieLocal>() {

    private val moviesDao = appDatabase.movieDao()
    private val remoteKeysDao = appDatabase.remoteKeysDao()
    private var currentPage = initialPageCount

    /**
     *
     * In cases where the local data needs to be fully refreshed,
     * initialize() should return InitializeAction.LAUNCH_INITIAL_REFRESH.
     * This causes the RemoteMediator to perform a remote refresh to fully reload the data.
     * Any remote APPEND or PREPEND loads wait for the REFRESH load to succeed before proceeding.
     */

    /**
     * In cases where the local data doesn't need to be refreshed, initialize()
     * should return InitializeAction.SKIP_INITIAL_REFRESH.
     * This causes the RemoteMediator to skip the remote refresh and load the cached data.
     *
     */
    /*override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
        return if (forceRefresh) {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else InitializeAction.SKIP_INITIAL_REFRESH

    }*/

    /**
     *   The load() method is responsible for
     *   updating the backing dataset and invalidating the PagingSource
     */
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieLocal>
    ): MediatorResult {
        return try {

            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: initialPageCount
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    // If remoteKeys is null, that means the refresh result is not in the database yet.
                    val prevKey = remoteKeys?.prevKey
                    if (prevKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    }
                    prevKey
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    // If remoteKeys is null, that means the refresh result is not in the database yet.
                    // We can return Success with endOfPaginationReached = false because Paging
                    // will call this method again if RemoteKeys becomes non-null.
                    // If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                    // the end of pagination for append.
                    val nextKey = remoteKeys?.nextKey
                    if (nextKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    } else nextKey
                }
            }


            val response = remoteData.getPopularMovies(page = page)

            val movies = response.movies?.map {
                it.toLocalMovie()
            } ?: emptyList()

            val endOfPaginationReached = movies.isEmpty()
            appDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    moviesDao.clearPopularMovies()
                    remoteKeysDao.clearRemoteKeys()
                }
                val prevKey = if (page == initialPageCount) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                Logger.i("keys:", "page: $page ,prevKey: $prevKey, nextKey: $nextKey")
                val keys = movies.map {
                    RemoteKeys(
                        movieId = it.id ?: -1,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                remoteKeysDao.insertAll(keys)
                moviesDao.insertMovies(movies)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }


    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieLocal>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                // Get the remote keys of the last item retrieved
                remoteKeysDao.remoteKeysRepoId(movie.id ?: -1)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieLocal>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                // Get the remote keys of the first items retrieved
                remoteKeysDao.remoteKeysRepoId(movie.id ?: -1)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieLocal>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { movieId ->
                remoteKeysDao.remoteKeysRepoId(movieId)
            }
        }
    }
}