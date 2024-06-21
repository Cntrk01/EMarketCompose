package com.emarket.emarketcompose.presentation.bottomnavigation

import com.emarket.emarketcompose.R

sealed class BottomNavState(val route: String, val icon: Int) {
    data object Home : BottomNavState(route = "Home", icon = R.drawable.baseline_home_24)
    data object Basket : BottomNavState(route = "Basket", icon = R.drawable.baseline_shopping_basket_24)
    data object Favorite : BottomNavState(route = "Favorite", icon = R.drawable.baseline_favorite_24)
    data object History : BottomNavState(route = "History", icon = R.drawable.baseline_person_24)
}