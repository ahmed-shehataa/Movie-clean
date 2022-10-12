package com.ashehata.movieclean.presentaion.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.ashehata.movieclean.R
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MoviesScreen() {
    val scaffoldState = rememberScaffoldState()
    Scaffold(scaffoldState = scaffoldState,
        topBar = {
            TopBar(onFilterClicked = {

            })
        }, content = {
            MoviesContent(scaffoldState, it)
        })
}

@Composable
fun MoviesContent(scaffoldState: ScaffoldState, it: PaddingValues) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(false),
        onRefresh = {

        },
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Last refresh: 2 min ago",
                fontSize = 20.sp,
                color = MaterialTheme.colors.primary
            )
            MoviesList(scaffoldState, it)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopBar(onFilterClicked: () -> Unit = {}) {
    TopAppBar {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.movie_app_name),
                color = Color.White,
                fontSize = 22.sp
            )

            DotsIcon(onFilterClicked)
        }
    }
}

@Composable
fun MoviesList(scaffoldState: ScaffoldState, paddingValues: PaddingValues) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 4.dp)
    ) {

        LazyVerticalGrid(
            columns = GridCells.Adaptive(100.dp),
            contentPadding = PaddingValues(
                start = 8.dp,
                top = 8.dp,
                end = 8.dp,
                bottom = 8.dp
            ),
        ) {

            (1..15).forEach { _ ->
                item {
                    MovieItem()
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

        }
    }
}

@Composable
fun MovieItem() {
    Column(
        Modifier
            .padding(12.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
            }
            .background(MaterialTheme.colors.secondary)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MovieImage()
        Text(
            text = "Title",
            fontSize = 18.sp,
            color = Color.White
        )
    }
}

@Composable
fun MovieImage() {
    Card(
        modifier = Modifier
            .size(70.dp)
            .clip(CircleShape)
            .border(
                1.5.dp,
                MaterialTheme.colors.secondary,
                CircleShape
            )
            .background(MaterialTheme.colors.primaryVariant)
            .clickable {

            }, elevation = 2.dp
    ) {
        LoadImageAsync(
            modifier = Modifier
                .padding(2.dp)
                .clip(CircleShape),
            "imageUrl"
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
        modifier = Modifier
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