package com.emarket.emarketcompose.components.search

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class EMarketSearchKtTest {

    val hint = "Search Item"
    var searchText = ""

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun searchTest(){
        composeTestRule.setContent {

            EMarketSearch(
                hint = hint,
                onSearch = {},
                onValueChange = {}
            )
        }

        composeTestRule.onNodeWithContentDescription("Search").performClick()
        //Icon(
        //                imageVector = Icons.Default.Search,
        //                contentDescription = "Search" buradaki descriptionu buldu ve icona tıklama işlemi yaptı
        //            )
    }

    @Test
    fun searchItem(){
        composeTestRule.setContent {
            EMarketSearch(
                hint = hint,
                onSearch = {},
                onValueChange = {
                    searchText = it
                }
            )
        }

        composeTestRule.onNodeWithText(hint).assertExists()

        val inputText = "Hello"
        composeTestRule.onNodeWithText(hint).performTextInput(inputText)

        composeTestRule.onNodeWithText(inputText).assertExists() //inputTextin göründüğünü kontrol ettim

        composeTestRule.runOnIdle {
            assert(searchText == inputText)
        }
    }
}