package com.ashehata.movieclean.presentaion.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ashehata.movieclean.R
import com.ashehata.movieclean.domain.models.Movie

@Composable
@Preview(showBackground = true)
fun MovieDetailsScreen(movie: Movie = Movie(id = -1, name = "Dummy", voteAverage = 7.5)) {

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Box(Modifier.weight(1f)) {
            LoadImageAsync(
                modifier = Modifier.fillMaxWidth(),
                imageUrl = movie.imageUrl
            )
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.TopEnd)
            ) {
                MovieRateBadge(movie.voteAverage, Modifier.align(Alignment.TopEnd), textSze = 25.sp)
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(12.dp)
        ) {
            KeyValueItem(stringResource(id = R.string.title), movie.name)
            KeyValueItem(stringResource(id = R.string.overview), movie.description, Int.MAX_VALUE)
        }
    }
}

@Composable
fun KeyValueItem(key: String, name: String, maxLines: Int = 1) {
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
