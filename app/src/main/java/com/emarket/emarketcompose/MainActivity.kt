package com.emarket.emarketcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.rememberNavController
import com.emarket.emarketcompose.navigations.EMarketNavigation
import com.emarket.emarketcompose.navigations.NavigationState
import com.emarket.emarketcompose.ui.theme.EMarketComposeTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EMarketComposeTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = colorResource(id = R.color.background))
                ) { innerPadding ->
                    val navigationController = rememberNavController()
                    EMarketNavigation(
                        navController = navigationController,
                        startDestination = NavigationState.OnBoardingRoute.route
                    )
                }
            }
        }
    }
}