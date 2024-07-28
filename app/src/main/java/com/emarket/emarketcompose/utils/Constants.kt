package com.emarket.emarketcompose.utils

import com.emarket.emarketcompose.presentation.bottomnavigation.BottomNavState

object Constants {
    const val PAGE_SIZE = 10

    val BOTTOM_NAV_ITEMS = listOf(
        BottomNavState.Home,
        BottomNavState.Basket,
        BottomNavState.Favorite,
        BottomNavState.History
    )

    val SORT_BY_LIST = listOf(
        "Old To New",
        "New To Old",
        "Price High To Low",
        "Price Low To High"
    )
}