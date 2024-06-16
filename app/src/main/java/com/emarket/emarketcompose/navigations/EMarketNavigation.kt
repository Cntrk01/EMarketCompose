package com.emarket.emarketcompose.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController

@Composable
fun EMarketNavigation(
    navController: NavHostController = rememberNavController()
){

    NavHost(
        navController = navController,
        startDestination = NavigationState.Home.route)
    {

        bottomNavigation()

    }
}

fun NavGraphBuilder.bottomNavigation(){
    navigation(startDestination = NavigationState.Home.route , route = NavigationState.AppRoute.route){
        composable(route = NavigationState.Home.route){

        }

        composable(route = NavigationState.Basket.route){

        }

        composable(route = NavigationState.Favorite.route){

        }

        composable(route = NavigationState.History.route){

        }

        composable(route = NavigationState.Filter.route){

        }

        composable(route = NavigationState.Detail.route){

        }
    }
}

fun NavGraphBuilder.onBoardingNavigation(){
    navigation(startDestination = "", route = NavigationState.OnBoardingRoute.route){
        
    }
}

