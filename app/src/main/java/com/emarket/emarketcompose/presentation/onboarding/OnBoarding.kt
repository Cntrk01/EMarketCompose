package com.emarket.emarketcompose.presentation.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import com.emarket.emarketcompose.components.onboarding.EMarketOnBoarding
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoarding(
    onBoardingFinish: () -> Unit,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val pagerState = rememberPagerState {
        onBoardingPages.size
    }
    //Fakat derivedStateOf ile currentPage durumunda değişiklik olduğunda çalıştırılacak böylelikle gereksiz liste oluşumunu ve blok çalışması olmayacak.
    val buttonState = remember {
        derivedStateOf {
            when (pagerState.currentPage) {
                0 -> listOf("", "Next")
                1 -> listOf("Back", "Next")
                2 -> listOf("Back", "Get Started")
                else -> listOf("", "")
            }
        }
    }

    Column {
        HorizontalPager(state = pagerState) { index ->
            EMarketOnBoarding(
                page = onBoardingPages[index],
                pagerState = pagerState,
                buttonState = buttonState.value,
                coroutineScope = coroutineScope,
                onBoardingFinish = {
                    onBoardingFinish()
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewOnBoarding() {
    OnBoarding(onBoardingFinish = {})
}