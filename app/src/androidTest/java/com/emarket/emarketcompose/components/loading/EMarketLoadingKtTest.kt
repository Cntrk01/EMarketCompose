package com.emarket.emarketcompose.components.loading

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.emarket.emarketcompose.components.loading_status.EMarketLoading
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class EMarketLoadingKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testEMarketLoadingDisplayed() {
        composeTestRule.setContent {
            EMarketLoading(modifier = Modifier.testTag("EMarketLoading"))
        }

        composeTestRule.onNodeWithTag("EMarketLoading").assertIsDisplayed()
    }
}