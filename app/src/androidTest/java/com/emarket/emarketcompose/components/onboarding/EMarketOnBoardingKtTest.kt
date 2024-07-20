import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.emarket.emarketcompose.components.onboarding.EMarketOnBoarding
import com.emarket.emarketcompose.presentation.onboarding.onBoardingPages
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EMarketOnBoardingKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    var pagerStates = 0

    @OptIn(ExperimentalFoundationApi::class)
    @Test
    fun firstPageTest() {
        composeTestRule.setContent {
            val mockCoroutineScope = rememberCoroutineScope()
            val pagerState = rememberPagerState(initialPage = 0) {
                onBoardingPages.size
            }
            pagerStates = pagerState.currentPage

            val buttonState = listOf("", "Next")

            EMarketOnBoarding(
                page = onBoardingPages[0],
                pagerState = pagerState,
                buttonState = buttonState,
                coroutineScope = mockCoroutineScope,
                onBoardingFinish = {}
            )
        }

        composeTestRule.onNodeWithText("Onboarding Page 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("This is onboarding page 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Next").assertIsDisplayed()
        composeTestRule.onNodeWithText("Back").assertIsNotDisplayed()

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Next").performClick()
        composeTestRule.runOnIdle {
            assert(pagerStates == 1)
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Test
    fun secondPageTest() {

        composeTestRule.setContent {
            val mockCoroutineScope = rememberCoroutineScope()

            val pagerState = rememberPagerState(initialPage = 1) {
                onBoardingPages.size
            }
            pagerStates = pagerState.currentPage

            val buttonState = listOf("Back", "Next")

            EMarketOnBoarding(
                page = onBoardingPages[1],
                pagerState = pagerState,
                buttonState = buttonState,
                coroutineScope = mockCoroutineScope,
                onBoardingFinish = {}
            )
        }

        composeTestRule.onNodeWithText("Onboarding Page 2").assertIsDisplayed()
        composeTestRule.onNodeWithText("This is onboarding page 2").assertIsDisplayed()
        composeTestRule.onNodeWithText("Next").assertIsDisplayed()
        composeTestRule.onNodeWithText("Back").assertIsDisplayed()

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Next").performClick()
        composeTestRule.runOnIdle {
            assert(pagerStates == 1)
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Test
    fun thirdPageTest() {

        val buttonState = listOf("Back", "Get Started")
        var finishCalled = false


        composeTestRule.setContent {
            val mockCoroutineScope = rememberCoroutineScope()

            val pagerState = rememberPagerState(initialPage = 2) {
                onBoardingPages.size
            }
            pagerStates = pagerState.currentPage

            EMarketOnBoarding(
                page = onBoardingPages[2],
                pagerState = pagerState,
                buttonState = buttonState,
                coroutineScope = mockCoroutineScope,
                onBoardingFinish = { finishCalled = true }
            )
        }

        composeTestRule.onNodeWithText("Onboarding Page 3").assertIsDisplayed()
        composeTestRule.onNodeWithText("This is onboarding page 3").assertIsDisplayed()
        composeTestRule.onNodeWithText("Get Started").assertIsDisplayed()
        composeTestRule.onNodeWithText("Back").assertIsDisplayed()
        composeTestRule.onNodeWithText("Get Started").performClick()

        composeTestRule.runOnIdle {
            assert(finishCalled)
        }
    }
}
