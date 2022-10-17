package com.ashehata.movieclean.presentaion.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.ashehata.movieclean.R
import com.ashehata.movieclean.domain.models.Movie
import com.ashehata.movieclean.presentaion.models.MoviesType
import com.ashehata.movieclean.presentaion.util.toRateColor
import com.ashehata.movieclean.presentaion.util.compose.items
import com.ashehata.movieclean.presentaion.viewModel.MoviesViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MoviesScreen(
    moviesViewModel: MoviesViewModel,
    onMovieClicked: (Movie) -> Unit = {},
) {
    // Handle config changes by saving MoviesType
    var movieType by rememberSaveable {
        mutableStateOf(MoviesType.POPULAR)
    }
    // Request the movies from VM
    moviesViewModel.moviesType.trySend(movieType)

    Scaffold(
        modifier = Modifier.testTag("movies_screen").semantics {
            testTagsAsResourceId = true
        }.background(MaterialTheme.colors.onSecondary),
        topBar = {
            TopBar { type -> movieType = type }
        },
        content = {
            it.toString()
            MoviesContent(moviesViewModel.moviesList, onMovieClicked, onRefresh = {
                moviesViewModel.refreshData.trySend(true)

            })
        })
}

@Composable
fun MoviesContent(
    moviesFlow: Flow<PagingData<Movie>>,
    onMovieClicked: (Movie) -> Unit,
    onRefresh: () -> Unit
) {
    val moviesItems: LazyPagingItems<Movie> = moviesFlow.collectAsLazyPagingItems()
    val isRefreshing = moviesItems.loadState.refresh is LoadState.Loading

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            onRefresh()
        },
    ) {
        MoviesUiStates(moviesItems)
        Box(modifier = Modifier.fillMaxSize()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                MoviesList(moviesFlow, onMovieClicked)
            }
        }
    }
}

@Composable
fun MoviesUiStates(moviesItems: LazyPagingItems<Movie>) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .height(maxHeight)
                .align(Alignment.Center)
        ) {
            with(moviesItems.loadState.refresh) {
                ShowLoadingState(this)
                ShowErrorState(this)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopBar(onFilterItemClicked: (MoviesType) -> Unit = {}) {
    TopAppBar(modifier = Modifier.semantics { contentDescription = "Movies_app_bar" }, title = {
        Title()
    }, actions = {
        AppBarActions(onFilterItemClicked)
    })

}

@Composable
fun AppBarActions(onFilterItemClicked: (MoviesType) -> Unit) {
    var mDisplayMenu by remember { mutableStateOf(false) }
    DotsIcon(onFilterClicked = {
        mDisplayMenu = !mDisplayMenu
    })
    // Creating a dropdown menu
    DropdownMenu(
        modifier = Modifier.semantics { contentDescription = "popup_menu" },
        expanded = mDisplayMenu,
        onDismissRequest = { mDisplayMenu = false }
    ) {

        DropdownMenuItem(onClick = {
            onFilterItemClicked(MoviesType.TOP_RATED)
            mDisplayMenu = false
        }) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Icon(imageVector = Icons.Filled.Star, contentDescription = null)
                Text(text = stringResource(id = R.string.top_rated))
            }
        }

        DropdownMenuItem(onClick = {
            onFilterItemClicked(MoviesType.POPULAR)
            mDisplayMenu = false
        }) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = null)
                Text(text = stringResource(id = R.string.popular))
            }
        }
    }
}

@Composable
fun Title() {
    Text(
        text = stringResource(id = R.string.movie_app_name),
        color = Color.White,
        fontSize = 22.sp
    )
}

@Composable
fun MoviesList(moviesFlow: Flow<PagingData<Movie>>, onMovieClicked: (Movie) -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 4.dp)
    ) {

        val moviesItems = moviesFlow.collectAsLazyPagingItems()

        LazyVerticalGrid(
            modifier = Modifier.testTag("myLazyVerticalGrid"),
            columns = GridCells.Adaptive(100.dp),
            contentPadding = PaddingValues(
                start = 8.dp,
                top = 8.dp,
                end = 8.dp,
                bottom = 8.dp
            ),
        ) {
            // Movies items
            items(moviesItems) { movie ->
                MovieItem(movie, onMovieClicked)
            }
            //Footer
            /*item {
                ShowLoadingProgress(moviesItems.loadState.refresh)
            }*/

        }
    }
}

@Composable
fun ShowLoadingProgress(loadState: LoadState) {
    if (loadState is LoadState.Loading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier.size(35.dp),
                color = MaterialTheme.colors.primary
            )
        }
    }
}

@Composable
fun ShowErrorState(loadState: LoadState) {
    if (loadState is LoadState.Error) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(200.dp),
                    painter = painterResource(id = R.drawable.ic_cloud_server_antenna),
                    contentDescription = null
                )
                Text(
                    text = stringResource(id = R.string.no_movies),
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.onPrimary,
                )
            }
        }
    }
}

@Composable
fun ShowLoadingState(loadState: LoadState) {
    if (loadState is LoadState.Loading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(35.dp)
                    .align(Alignment.Center),
                color = MaterialTheme.colors.primary
            )
        }
    }
}

@Composable
fun MovieItem(movie: Movie?, onMovieClicked: (Movie) -> Unit) {
    movie?.let {
        Column(
            Modifier
                .padding(12.dp)
                .clip(RoundedCornerShape(12.dp))
                .clickable {
                    onMovieClicked(movie)
                }
                .background(MaterialTheme.colors.secondary)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MovieImageRate(movie)
            Text(
                text = movie.name,
                fontSize = 18.sp,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun MovieImageRate(movie: Movie) {
    Box {
        MovieImage(movie.imageUrlSmall)
        MovieRateBadge(movie.voteAverage, Modifier.align(Alignment.TopEnd))
    }
}

@Composable
fun MovieRateBadge(voteAverage: Double, Modifier: Modifier, textSze: TextUnit = 16.sp) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(voteAverage.toRateColor())

    ) {
        Text(
            modifier = Modifier
                .padding(4.dp),
            text = voteAverage.toString(),
            fontSize = textSze,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun MovieImage(imageUrl: String) {
    Card(
        modifier = Modifier
            .size(70.dp)
            .clip(CircleShape)
            .border(
                1.5.dp,
                MaterialTheme.colors.secondary,
                CircleShape
            )
            .background(MaterialTheme.colors.primaryVariant), elevation = 2.dp
    ) {
        LoadImageAsync(
            modifier = Modifier
                .padding(2.dp)
                .clip(CircleShape),
            imageUrl
        )
    }
}

@Composable
fun LoadImageAsync(
    modifier: Modifier,
    imageUrl: String?,
    @DrawableRes placeholder: Int = R.drawable.placeholder_image,
    @DrawableRes onError: Int = R.drawable.error_image,
    cacheEnabled: Boolean = true,
    contentScale: ContentScale = ContentScale.Crop
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .also {
                if (cacheEnabled) {
                    it.diskCachePolicy(CachePolicy.ENABLED)
                }
            }
            .build(),
        placeholder = painterResource(placeholder),
        contentDescription = "Image",
        contentScale = contentScale,
        error = painterResource(onError),
        modifier = modifier
    )
}

@Composable
fun DotsIcon(
    onFilterClicked: () -> Unit,
    size: Dp = 50.dp
) {
    Box(
        modifier = Modifier.semantics{contentDescription = "filter_icon"}
            .size(size)
            .clip(RoundedCornerShape(50))
            .padding(4.dp)
            .clickable {
                onFilterClicked()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_filter_list),
            tint = MaterialTheme.colors.secondary,
            contentDescription = null
        )
    }
}

@Composable
fun StatusBarColor() {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = MaterialTheme.colors.primary
    )
}