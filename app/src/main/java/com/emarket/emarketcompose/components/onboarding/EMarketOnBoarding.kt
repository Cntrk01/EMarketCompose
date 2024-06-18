package com.emarket.emarketcompose.components.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.emarket.emarketcompose.R
import com.emarket.emarketcompose.components.button.EMarketButton
import com.emarket.emarketcompose.components.onboarding.indicator.EMarketIndicator
import com.emarket.emarketcompose.onboarding.OnBoardingPage
import com.emarket.emarketcompose.onboarding.onBoardingPages
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EMarketOnBoarding(
    modifier: Modifier = Modifier,
    page: OnBoardingPage,
    pagerState: PagerState,
    buttonState: State<List<String>>,
    coroutineScope: CoroutineScope,
    onBoardingFinish: () -> Unit
) {
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    Column(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f),
            painter = painterResource(id = page.image),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen._15dp)))

        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen._10dp))
                        .weight(0.6f),
                    text = page.title,
                    style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(id = R.color.bottomNavTextColor),
                    fontSize = 24.sp
                )

                EMarketIndicator(
                    modifier = Modifier.padding(end = dimensionResource(id = R.dimen._20dp)),
                    pageSize = pagerState.pageCount,
                    selectedPage = pagerState.currentPage
                )
            }
            Text(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen._10dp))
                    .weight(1f)
                    .fillMaxWidth(),
                text = page.description,
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(id = R.color.bottomNavTextColor),
                fontSize = 18.sp
            )

            Spacer(
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen._10dp))
            )

            OnBoardingContent(
                pagerState = pagerState,
                buttonState = buttonState,
                coroutineScope = coroutineScope,
                onBoardingFinish = {
                    onBoardingFinish()
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingContent(
    pagerState: PagerState,
    buttonState: State<List<String>>,
    coroutineScope: CoroutineScope,
    onBoardingFinish: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = dimensionResource(id = R.dimen._20dp),
                end = dimensionResource(id = R.dimen._20dp),
                bottom = dimensionResource(id = R.dimen._20dp)
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        when {
            pagerState.pageCount > 0 && buttonState.value[0].isNotEmpty() -> {
                EMarketButton(
                    text = buttonState.value[0],
                    clickButton = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
                        }
                    })
            }
            else -> Spacer(modifier = Modifier.weight(1f))
        }

        EMarketButton(
            text = buttonState.value[1],
            clickButton = {
                if (pagerState.currentPage == 2) {
                    onBoardingFinish()
                } else {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                    }
                }
            })
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun PreviewEMarketOnBoarding() {
    val pagerState = rememberPagerState(initialPage = 0) {
        onBoardingPages.size
    }

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

    val mockCoroutineScope = rememberCoroutineScope()

    EMarketOnBoarding(
        page = OnBoardingPage(
            title = "Hello",
            description = "Helooo 123 12 3",
            image = R.drawable.onboarding1
        ),
        pagerState = pagerState,
        buttonState = buttonState,
        coroutineScope = mockCoroutineScope,
        onBoardingFinish = {

        }
    )
}