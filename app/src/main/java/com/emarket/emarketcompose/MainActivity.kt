package com.emarket.emarketcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.res.colorResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.emarket.emarketcompose.navigations.EMarketNavigation
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

        installSplashScreen().apply {
            onBoardingViewModel.splashCondition
        }

        setContent {
            EMarketComposeTheme {

                setThemeColor()

                    val navigationController = rememberNavController()
                    val routeStatus = onBoardingViewModel.startDestination

                    EMarketNavigation(
                        navController = navigationController,
                        startDestination = routeStatus,
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