package com.emarket.emarketcompose.presentation.basket

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun BasketPage(
    modifier: Modifier = Modifier,
    basketViewModel: BasketViewModel = hiltViewModel()
) {
    basketViewModel.getBasket()
    val x = basketViewModel.basket.observeAsState()

    println(x.value)
}