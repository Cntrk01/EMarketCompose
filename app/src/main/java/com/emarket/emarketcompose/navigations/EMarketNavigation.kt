package com.emarket.emarketcompose.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.emarket.emarketcompose.presentation.home.HomePage
import com.emarket.emarketcompose.presentation.onboarding.OnBoarding

@Composable
fun EMarketNavigation(
    navController: NavHostController,
    startDestination: String,
    onBoardingFinish: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    )
    {
        bottomNavigation()
        onBoardingNavigation(onBoardingFinish = {
            onBoardingFinish()
        })
    }
}

fun NavGraphBuilder.bottomNavigation() {
    navigation(
        startDestination = NavigationState.Home.route,
        route = NavigationState.AppRoute.route
    ) {
        composable(route = NavigationState.Home.route) {
            HomePage()
        }

        composable(route = NavigationState.Basket.route) {

        }

        composable(route = NavigationState.Favorite.route) {

        }

        composable(route = NavigationState.History.route) {

        }

        composable(route = NavigationState.Filter.route) {

        }

        composable(route = NavigationState.Detail.route) {

        }
    }
}

fun NavGraphBuilder.onBoardingNavigation(onBoardingFinish: () -> Unit) {
    navigation(
        startDestination = NavigationState.OnBoarding.route,
        route = NavigationState.OnBoardingRoute.route
    ) {
        composable(route = NavigationState.OnBoarding.route) {
            OnBoarding(onBoardingFinish = {
                onBoardingFinish()
            })
        }
    }
}

