package com.ashehata.movieclean.data.mappers.image

import java.util.*

const val IMAGE_BASE_URL = "http://image.tmdb.org/t/p/"

enum class ImageSize {
    W92,
    W54,
    W185,
    W342,
    W500,
    W780,
    ORIGINAL
}
abstract class ImageMapper(
    private val baseUrl: String = IMAGE_BASE_URL,
    private val imageSize: ImageSize = ImageSize.W185,
    private val imagePath: String = ""
) {
    open fun getFullImageUrl(): String {
        return baseUrl + imageSize.toString().toLowerCase(Locale.getDefault()) + imagePath
    }
}

class Image780(imagePath: String) : ImageMapper(
    imageSize = ImageSize.W780,
    imagePath = imagePath
)

class Image185(imagePath: String) : ImageMapper(
    imageSize = ImageSize.W185,
    imagePath = imagePath

)

