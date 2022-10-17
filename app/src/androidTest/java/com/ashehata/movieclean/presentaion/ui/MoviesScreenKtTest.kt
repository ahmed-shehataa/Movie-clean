package com.ashehata.movieclean.presentaion.ui

import android.support.test.filters.LargeTest
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.findNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import com.ashehata.movieclean.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class MoviesScreenKtTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<HomeActivity>()


    @Test
    fun moviesScreen_is_app_bar_displayed() {
        val context = composeTestRule.activity.applicationContext
        // asset is App Bar is displayed
        composeTestRule.onNodeWithContentDescription("Movies_app_bar").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("filter_icon").assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.movie_app_name))
            .assertIsDisplayed()
    }

    @Test
    fun moviesScreen_open_filter_popup() {
        val context = composeTestRule.activity.applicationContext
        // click on filter icon
        composeTestRule.onRoot().printToLog("currentLabelExists")

        composeTestRule
            .onNodeWithContentDescription("filter_icon")
            .performClick()

        composeTestRule
            .onNodeWithContentDescription("popup_menu")
            .assertIsDisplayed()

    }

    @Test
    fun moviesScreen_open_movie_details_screen() {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        composeTestRule.waitForIdle()
        val lazyVerticalColumn: UiObject2 = device.findObject(By.res("myLazyVerticalGrid"))

        if ((lazyVerticalColumn.childCount != 0)) {
            lazyVerticalColumn.children[0].click()
        }

        composeTestRule.onNodeWithTag("movie_details").assertIsDisplayed()
    }

    @Test
    fun moviesScreen_open_movie_details_screen_then_back() {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        composeTestRule.waitForIdle()
        val lazyVerticalColumn: UiObject2 = device.findObject(By.res("myLazyVerticalGrid"))

        if ((lazyVerticalColumn.childCount != 0)) {
            lazyVerticalColumn.children[0].click()
        }

        composeTestRule.onNodeWithTag("movie_details").assertIsDisplayed()
        device.pressBack()
        composeTestRule.onNodeWithTag("movies_screen").assertIsDisplayed()
    }


}