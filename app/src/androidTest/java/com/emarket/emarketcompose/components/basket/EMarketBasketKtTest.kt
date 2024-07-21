package com.emarket.emarketcompose.components.basket

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.emarket.emarketcompose.components.basket_item_row.EMarketBasketItemRow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.math.min

@RunWith(JUnit4::class)
class EMarketBasketKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val titleText = "Home"
    private val price = "100"
    private var totalOneItem = "1"

    @Test
    fun checkTitleAndPriceExist() {
        composeTestRule.setContent {
            EMarketBasketItemRow(
                price = price,
                title= titleText,
                totalOneItem = totalOneItem,
                plusButton = {},
                minusButton = {}
            )
        }

        composeTestRule.onNodeWithText(titleText).isDisplayed()
        composeTestRule.onNodeWithText(price).isDisplayed()
    }
    
    //Ekleme durumunu test ettim
    @Test
    fun checkIsPlusValue(){
        composeTestRule.setContent {
            EMarketBasketItemRow(
                price = price,
                title= titleText,
                totalOneItem = totalOneItem,
                plusButton = {
                    totalOneItem = (totalOneItem.toInt() + 1).toString()
                },
                minusButton = {}
            )
        }

        composeTestRule.onNodeWithText("+")
            .assertIsDisplayed()
            .performClick()

        assert(totalOneItem == "2")
    }

    //Çıkarma durumunu test ettim
    @Test
    fun checkIsMinusValue(){
        composeTestRule.setContent {
            EMarketBasketItemRow(
                price = price,
                title= titleText,
                totalOneItem = totalOneItem,
                plusButton = {},
                minusButton = {
                    totalOneItem = (totalOneItem.toInt() - 1).toString()
                }
            )
        }

        composeTestRule.onNodeWithText("-")
            .assertIsDisplayed()
            .performClick()

        assert(totalOneItem == "0")
    }
}