package com.ashehata.movieclean.presentaion.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ashehata.movieclean.R
import com.ashehata.movieclean.domain.models.Movie
import com.ashehata.movieclean.presentaion.util.toRateColor
import com.ashehata.movieclean.presentaion.util.compose.CircularProgressBar

@Composable
@Preview(showBackground = true)
fun MovieDetailsScreen(movie: Movie = Movie(id = -1, name = "Dummy", voteAverage = 7.5)) {
    Column(
        Modifier.testTag("movie_details"), verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        val scrollState = rememberScrollState()
        val transition =
            updateTransition(scrollState.value != 0, label = "")
        val size by transition.animateDp(label = "") { isScrolling ->
            if (isScrolling) 300.dp else 500.dp
        }
        Box(
            Modifier
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                )
                .height(size)
        ) {
            LoadImageAsync(
                modifier = Modifier
                    .fillMaxWidth(),
                imageUrl = movie.imageUrlFull
            )
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(scrollState)
            ) {
                KeyValueItem(stringResource(id = R.string.title), movie.name)
                KeyValueItem(
                    stringResource(id = R.string.overview),
                    movie.description
                )
                KeyValueItem(
                    stringResource(id = R.string.overview),
                    movie.description
                )
                KeyValueItem(
                    stringResource(id = R.string.overview),
                    movie.description
                )
            }
            Box(
                modifier = Modifier, contentAlignment = Alignment.Center
            ) {
                CircularProgressBar(
                    percentage = movie.voteAverage.toFloat() / 10,
                    number = movie.voteAverage.toFloat(),
                    color = movie.voteAverage.toRateColor(),
                    fontSize = 20.sp,
                    size = 25.dp
                )
            }
        }

    }
}

@Composable
fun KeyValueItem(key: String, name: String, maxLines: Int = Int.MAX_VALUE) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = key,
            fontSize = 20.sp,
            color = Color.Black,
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier
                .padding(4.dp),
            text = name,
            fontStyle = FontStyle.Italic,
            fontSize = 16.sp,
            color = Color.Black,
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis
        )
    }
}
