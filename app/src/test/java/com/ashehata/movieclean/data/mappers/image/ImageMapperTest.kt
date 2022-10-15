package com.ashehata.movieclean.data.mappers.image

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.util.*


class FakeImage(
    baseUrl: String,
    imageSize: ImageSize,
    imagePath: String
) : ImageMapper(baseUrl, imageSize, imagePath)

class ImageMapperTest {

    @Test
    fun `getFullImageUrl() from image size of type W54`() {
        val fakeImagePath = FakeImage(
            baseUrl = "baseImage/",
            imageSize = ImageSize.W54,
            imagePath = "/path.png"
        ).getFullImageUrl()

        val expected =
            "baseImage/${ImageSize.W54.toString().toLowerCase(Locale.getDefault())}/path.png"

        assertThat(expected).isEqualTo(fakeImagePath)

    }


    @Test
    fun `getFullImageUrl() from image size of type W92`() {
        val fakeImagePath = FakeImage(
            baseUrl = "baseImage/",
            imageSize = ImageSize.W92,
            imagePath = "/path.png"
        ).getFullImageUrl()

        val expected =
            "baseImage/${ImageSize.W92.toString().toLowerCase(Locale.getDefault())}/path.png"

        assertThat(expected).isEqualTo(fakeImagePath)

    }

    @Test
    fun `getFullImageUrl() from image size of type W185`() {
        val fakeImagePath = FakeImage(
            baseUrl = "baseImage/",
            imageSize = ImageSize.W185,
            imagePath = "/path.png"
        ).getFullImageUrl()

        val expected =
            "baseImage/${ImageSize.W185.toString().lowercase()}/path.png"

        assertThat(expected).isEqualTo(fakeImagePath)

    }

    @Test
    fun `getFullImageUrl() from image size of type W500`() {
        val fakeImagePath = FakeImage(
            baseUrl = "baseImage/",
            imageSize = ImageSize.W500,
            imagePath = "/path.png"
        ).getFullImageUrl()

        val expected =
            "baseImage/${ImageSize.W500.toString().toLowerCase(Locale.getDefault())}/path.png"

        assertThat(expected).isEqualTo(fakeImagePath)

    }

    @Test
    fun `getFullImageUrl() from image size of type W780`() {
        val fakeImagePath = FakeImage(
            baseUrl = "baseImage/",
            imageSize = ImageSize.W780,
            imagePath = "/path.png"
        ).getFullImageUrl()

        val expected =
            "baseImage/${ImageSize.W780.toString().lowercase()}/path.png"

        assertThat(expected).isEqualTo(fakeImagePath)

    }

    @Test
    fun `getFullImageUrl() from image size of type ORIGINAL`() {
        val fakeImagePath = FakeImage(
            baseUrl = "baseImage/",
            imageSize = ImageSize.ORIGINAL,
            imagePath = "/path.png"
        ).getFullImageUrl()

        val expected =
            "baseImage/${ImageSize.ORIGINAL.toString().lowercase()}/path.png"

        assertThat(expected).isEqualTo(fakeImagePath)

    }


}
