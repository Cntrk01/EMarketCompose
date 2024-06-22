package com.emarket.emarketcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.rememberNavController
import com.emarket.emarketcompose.navigations.EMarketNavigation
import com.emarket.emarketcompose.navigations.NavigationState
import com.emarket.emarketcompose.presentation.onboarding.OnBoardingViewModel
import com.emarket.emarketcompose.ui.theme.EMarketComposeTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val onBoardingViewModel: OnBoardingViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge() //bu full screen yapıyor ve alttaki butonlar navigation barın altına taşıyordu ondan dolayı bunu commentledim

        setContent {
            EMarketComposeTheme {

                setThemeColor()

                    val navigationController = rememberNavController()
                    val routeStatus = onBoardingViewModel.readStatus.collectAsState()

                    EMarketNavigation(
                        navController = navigationController,
                        startDestination = if (routeStatus.value) NavigationState.AppRoute.route else NavigationState.OnBoardingRoute.route,
                        onBoardingFinish = {
                            onBoardingViewModel.updateDataStore()
                        }
                    )
            }
        }
    }
}

@Composable
private fun setThemeColor(){
    val isSystemInDarkMode = isSystemInDarkTheme()
    val systemController = rememberSystemUiController()
    val systemTheme = if (isSystemInDarkMode)
                           colorResource(id = R.color.primaryColor)
                      else colorResource(id = R.color.primaryColor)
    SideEffect {
        systemController.setSystemBarsColor(
            color = systemTheme,
            darkIcons = !isSystemInDarkMode
        )
    }
}