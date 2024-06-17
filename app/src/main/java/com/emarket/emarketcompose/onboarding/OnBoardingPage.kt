package com.emarket.emarketcompose.onboarding

import androidx.annotation.DrawableRes
import com.emarket.emarketcompose.R

data class OnBoardingPage(
    val title : String,
    val description : String,
    @DrawableRes val image : Int
)

val onBoardingPages = listOf(
    OnBoardingPage(
        title = "Onboarding Page 1",
        description = "This is onboarding page 1",
        R.drawable.onboarding1
    ),

    OnBoardingPage(
        title = "Onboarding Page 2",
        description = "This is onboarding page 2",
        R.drawable.onboarding2
    ),

    OnBoardingPage(
        title = "Onboarding Page 3",
        description = "This is onboarding page 3",
        R.drawable.onboarding3
    ),
)