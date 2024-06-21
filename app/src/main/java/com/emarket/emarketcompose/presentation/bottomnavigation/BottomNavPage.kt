package com.emarket.emarketcompose.presentation.bottomnavigation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.emarket.emarketcompose.R
import com.emarket.emarketcompose.components.bottom_navigation.EMarketBottomNavigation
import com.emarket.emarketcompose.components.header.EMarketHeader
import com.emarket.emarketcompose.navigations.NavigationState
import com.emarket.emarketcompose.presentation.basket.BasketPage
import com.emarket.emarketcompose.presentation.favorite.FavoritePage
import com.emarket.emarketcompose.presentation.history.HistoryPage
import com.emarket.emarketcompose.presentation.home.HomePage

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavPage() {
    val bottomNavList = listOf(
        BottomNavState.Home,
        BottomNavState.Basket,
        BottomNavState.Favorite,
        BottomNavState.History
    )

    val navController = rememberNavController()
    //backStack = NavController'ın geri yığınındaki (back stack) mevcut girişin State (durum) olarak gözlemlenmesini sağla
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    selectedItem = remember(key1 = backStackState) {
        when (backStackState?.destination?.route) {
            BottomNavState.Home.route -> 0
            BottomNavState.Basket.route -> 1
            BottomNavState.Favorite.route -> 2
            BottomNavState.History.route -> 3
            else -> 0
        }
    }

    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == BottomNavState.Home.route ||
                backStackState?.destination?.route == BottomNavState.Basket.route ||
                backStackState?.destination?.route == BottomNavState.Favorite.route ||
                backStackState?.destination?.route == BottomNavState.History.route
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background)),
        bottomBar = {
            if (isBottomBarVisible) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = colorResource(id = R.color.background)
                        )
                ) {
                    bottomNavList.forEachIndexed { index, bottomNavState ->

                        EMarketBottomNavigation(
                            modifier = Modifier
                                .weight(1f)
                                .background(color = colorResource(id = R.color.primaryColor))
                                .padding(dimensionResource(id = R.dimen._5dp)),
                            icon = bottomNavState.icon,
                            showIconText = bottomNavState.route,
                            selectedItemPosition = index,
                            isSelected = index == selectedItem,
                        ) {
                            if (index != selectedItem) {
                                navController.navigate(bottomNavState.route)
                            }
                        }
                    }
                }
            }
        },
//        topBar = {
//            EMarketHeader()
//        }
    ) { paddingValues ->

        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = NavigationState.Home.route
        ) {
            composable(route = NavigationState.Home.route) {
                HomePage()
            }

            composable(route = NavigationState.Basket.route) {
                BasketPage()
            }

            composable(route = NavigationState.Favorite.route) {
                FavoritePage() // Implement your FavoritePage here
            }

            composable(route = NavigationState.History.route) {
                HistoryPage() // Implement your HistoryPage here
            }


            composable(route = NavigationState.Filter.route) {
                // FilterPage() // Implement your FilterPage here
            }

            composable(route = NavigationState.Detail.route) {
                // DetailPage() // Implement your DetailPage here
            }
        }
    }
}