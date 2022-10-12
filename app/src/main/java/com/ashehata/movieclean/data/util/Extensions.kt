package com.ashehata.movieclean.data.util

import com.ashehata.movieclean.Logger
import java.util.*


private const val IMAGE_BASE_URL = "http://image.tmdb.org/t/p/"

enum class ImageSize {
    W92,
    W_54,
    W185,
    W342,
    W500,
    W780,
    ORIGINAL
}

fun String?.toRealPath(imageSize: ImageSize = ImageSize.W185): String {
    val realPath = IMAGE_BASE_URL + imageSize.toString().toLowerCase(Locale.getDefault()) + this
    Logger.i("toRealPath", realPath)
    return realPath
}