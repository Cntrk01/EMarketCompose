package com.emarket.emarketcompose.components.home_card

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import com.emarket.emarketcompose.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class EMarketHomeCardKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var context: Context
    private var clickButtonValue = false
    private var clickFavoriteButtonValue = false
    private var clickDetailButtonValue = false

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun cardPriceAndDescriptionIsDisplayed() {
        composeTestRule.setContent {
            EMarketHomeCard(
                image = R.drawable.empty_image.toString(),
                price = "$100",
                description = "Home",
                clickDetail = {},
                clickFavorite = {},
                clickAddToCardButton = {}
            )
        }
        composeTestRule.onNodeWithText("Home").isDisplayed()
        composeTestRule.onNodeWithText("$100").isDisplayed()
    }

    //Click işlemleri çalışıyor mu diye test ettim
    @Test
    fun cardButtonClickTest() {
        composeTestRule.setContent {
            EMarketHomeCard(
                modifier = Modifier.testTag("CardClick"),
                image = R.drawable.empty_image.toString(),
                price = "$100",
                description = "Home",
                clickDetail = {
                    clickDetailButtonValue = true
                },
                clickFavorite = {
                    clickFavoriteButtonValue = true
                },
                clickAddToCardButton = {
                    clickButtonValue = true
                }
            )
        }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Add to Card").assertExists().performClick() //assertExist mevcutta böyle bir bileşen varmı kontrol eder isDisplayed da ekranda görünüyor mu diye kontrol eder
//        composeTestRule.onNode( //yukardaki işlem tek satırda yapar
//            hasText("Add to Card")
//                    and
//                    hasClickAction() //dediğimizde hastexti o olan ve yanlızca click olabilen bir buton varsa çalışacaktır ekranda iki farklı durumdan buton olanı click ozelliği olanı ayırır
//        ).performClick()
        composeTestRule.onNodeWithText("Add to Card").isDisplayed()
        composeTestRule.onNodeWithContentDescription("Favorite").performClick()
        composeTestRule.onNodeWithTag("CardClick").performClick()

        composeTestRule.runOnIdle {
            assert(clickFavoriteButtonValue)
            assert(clickDetailButtonValue)
            //!!!!!!!! Component ekranda gözükmüyordu yani buton . Bundan dolayı da tıklama işlemi burada fail dönüyordu.Eğer ekranda gözükmeyen bir item var ve buna erişip tıklama.kontrol yapıyorsam bu işlem çalışmaz !
            //Aslında performClick işlemi çalışıyordu ama clickButton blogu çalışmıyordu ve true değer set edilmiyordu.
            //Sebebi ise image butonu eziyordu ve buton gözükmüyordu.
            assert(clickButtonValue)

        }
    }
    //favori butonuna tıklayınca true false değerler gözüküyor mu diye kontrol sağladım
    @Test
    fun favoriteButtonStatusTest(){
        var isShowStar by mutableStateOf(false)

        composeTestRule.setContent {
            EMarketHomeCard(
                modifier = Modifier.testTag("CardClick"),
                image = R.drawable.empty_image.toString(),
                price = "$100",
                description = "Home",
                clickDetail = {},
                clickFavorite = {
                    isShowStar = !isShowStar
                },
                clickAddToCardButton = {},
                isShowStar = isShowStar
            )
        }
        composeTestRule.onNodeWithContentDescription("Favorite")
            .performClick()
            .assertIsDisplayed()
            .assertExists()
        assert(isShowStar)

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithContentDescription("Favorite")
            .performClick()
            .assertIsDisplayed()
            .assertExists()

        assert(!isShowStar)
    }
}