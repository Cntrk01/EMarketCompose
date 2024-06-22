package com.emarket.emarketcompose.presentation.bottomnavigation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.emarket.emarketcompose.R
import com.emarket.emarketcompose.components.bottom_navigation.EMarketBottomNavigation
import com.emarket.emarketcompose.components.header.EMarketHeader
import com.emarket.emarketcompose.components.header.HeaderType
import com.emarket.emarketcompose.domain.repository.model.EMarketItem
import com.emarket.emarketcompose.navigations.NavigationState
import com.emarket.emarketcompose.presentation.basket.BasketPage
import com.emarket.emarketcompose.presentation.detail.DetailPage
import com.emarket.emarketcompose.presentation.favorite.FavoritePage
import com.emarket.emarketcompose.presentation.history.HistoryPage
import com.emarket.emarketcompose.presentation.home.HomePage
import com.emarket.emarketcompose.presentation.home.HomeViewModel
import com.emarket.emarketcompose.utils.Constants.BOTTOM_NAV_ITEMS
import com.emarket.emarketcompose.utils.navigateToDetails
import com.emarket.emarketcompose.utils.navigateToTap

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavPage() {

    val navController = rememberNavController()
    //backStack = NavController'ın geri yığınındaki (back stack) mevcut girişin State (durum) olarak gözlemlenmesini sağla
    val backStackState by navController.currentBackStackEntryAsState()
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
    val currentRoute = backStackState?.destination?.route

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
            BottomBarFunc(
                isBottomBarVisible = isBottomBarVisible,
                navController = navController,
                selectedItem = selectedItem
            )
        },
        topBar = {
            TopBarFunc(
                currentRoute = currentRoute,
                navController = navController
            )
        }
    ) { paddingValues ->

        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = NavigationState.Home.route
        ) {
            composable(route = NavigationState.Home.route) {
                val homeViewModel: HomeViewModel = hiltViewModel()
                val state = homeViewModel.homeDataState.collectAsState().value

                HomePage(
                    homeState = state,
                    viewModel = homeViewModel,
                    clickDetail = {
                        navigateToDetails(
                            navController = navController,
                            eMarketItem = it
                        )
                    })
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
                DetailPage()
            }
        }
    }
}

@Composable
fun TopBarFunc(currentRoute: String?, navController: NavHostController) {
    when (currentRoute) {
        NavigationState.Detail.route -> {
            EMarketHeader(
                headerTitle = "Detail",
                headerType = HeaderType.MULTI,
                backClick = { navController.popBackStack() })
        }

        NavigationState.Home.route -> {
            EMarketHeader(
                headerTitle = "Home",
                headerType = HeaderType.SIMPLE
            )
        }

        NavigationState.Basket.route -> {
            EMarketHeader(
                headerTitle = "Basket",
                headerType = HeaderType.SIMPLE
            )
        }

        NavigationState.Favorite.route -> {
            EMarketHeader(
                headerTitle = "Favorite",
                headerType = HeaderType.SIMPLE
            )
        }

        NavigationState.History.route -> {
            EMarketHeader(
                headerTitle = "History",
                headerType = HeaderType.SIMPLE
            )
        }

        else -> {
            EMarketHeader(
                headerTitle = "E-Market",
                headerType = HeaderType.SIMPLE
            )
        }
    }
}

@Composable
fun BottomBarFunc(
    isBottomBarVisible: Boolean,
    navController: NavHostController,
    selectedItem: Int
) {
    AnimatedVisibility(
        visible = isBottomBarVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorResource(id = R.color.background)
                )
        ) {
            BOTTOM_NAV_ITEMS.forEachIndexed { index, bottomNavState ->

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
                        navigateToTap(
                            navController = navController,
                            route = bottomNavState.route
                        )
                        //navController.navigate(bottomNavState.route)
                    }
                }
            }
        }
    }
}