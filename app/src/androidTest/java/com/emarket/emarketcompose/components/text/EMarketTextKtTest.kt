package com.emarket.emarketcompose.components.text

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.emarket.emarketcompose.ui.theme.EMarketComposeTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class EMarketTextKtTest {

    val text = "Hello"

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun textTest() {
        composeTestRule.setContent {
            EMarketComposeTheme {
                EMarketText(
                    text = text,
                    fontStyle = FontStyle.Normal,
                    textColor = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                )
            }
        }

        composeTestRule.onNodeWithText(text).assertIsDisplayed()
        composeTestRule.onNodeWithText(text).performClick()
    }
}