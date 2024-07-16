package com.emarket.emarketcompose.components.button

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.emarket.emarketcompose.ui.theme.EMarketComposeTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EMarketButtonKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

//    @Test
//    fun eMarketButtonDisplayedWithCorrectText() {
//        val buttonText = "Add to Cart"
//
//        composeTestRule.setContent {
//            EMarketComposeTheme {
//                EMarketButton(text = buttonText, clickButton = {})
//            }
//        }
//
//        composeTestRule.onNodeWithText(buttonText).assertIsDisplayed()
//    }

    @Test
    fun EMarketButton() {
        var clicked = false

        composeTestRule.setContent {
            EMarketComposeTheme {
                EMarketButton(
                    text = "Button",
                    clickButton = { clicked = true }
                )
            }
        }
        //composeTestRule.onNodeWithText("Button").assertDoesNotExist()

        // Test clicking action after performing click
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Button").performClick()
        // Assert that the click action was triggered
        composeTestRule.runOnIdle {
            assert(clicked)
        }
    }
}