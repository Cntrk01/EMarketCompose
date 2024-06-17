package com.emarket.emarketcompose.navigations

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.emarket.emarketcompose.onboarding.OnBoarding

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
            val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
            Box(
                modifier = Modifier
                    .padding(bottom = systemBarsPadding.calculateBottomPadding())
            ) {
                OnBoarding(onBoardingFinish = {
                    onBoardingFinish()
                })
            }
        }
    }
}

