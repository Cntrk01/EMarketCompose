package com.emarket.emarketcompose.navigations

sealed class NavigationState(val route : String) {
    data object OnBoarding : NavigationState("OnBoardingPage")
    data object Home : NavigationState("Home")
    data object Basket : NavigationState("Basket")
    data object Favorite : NavigationState("Favorite")
    data object History : NavigationState("History")
    data object Filter : NavigationState("Filter")
    data object Detail : NavigationState("Detail")

    //TODO : Route yöentimini buradan sağlayacağım.
    data object AppRoute : NavigationState("AppRoute")
    data object OnBoardingRoute : NavigationState("OnBoarding")
}