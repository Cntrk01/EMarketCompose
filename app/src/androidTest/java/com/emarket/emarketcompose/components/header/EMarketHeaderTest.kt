package com.emarket.emarketcompose.components.header

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class EMarketHeaderTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    var backClickStatus = false

    @Test
    fun headerTitleExists() {
        composeTestRule.setContent {
            EMarketHeader(headerTitle = "Title")
        }
        composeTestRule.onNodeWithText("Title").assertIsDisplayed()
    }

    //Simple durumda icon gözükmemeli ve click işlemi olmamalı.
    @Test
    fun headerCheckSimpleStatus() {
        composeTestRule.setContent {
            EMarketHeader(
                headerTitle = "Title",
                headerType = HeaderType.SIMPLE,
                backClick = {
                    backClickStatus = true
                }
            )
        }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithContentDescription("Back").assertDoesNotExist() //görünmediğini kontrol eder. Zaten yoksa true döner ve performClick işlemi yapmamıza gerek yoktur.Çünkü yok :)
        composeTestRule.runOnIdle {
            assert(!backClickStatus)
        }
    }

    //Multi durumda icon gözükmeli ve click işlemi olmalı.
    @Test
    fun headerCheckMultiStatus() {

        composeTestRule.setContent {
            EMarketHeader(
                headerTitle = "Title",
                headerType = HeaderType.MULTI,
                backClick = {
                    backClickStatus = true
                }
            )
        }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithContentDescription("Back").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        composeTestRule.runOnIdle {
            assert(backClickStatus)
        }
    }
}