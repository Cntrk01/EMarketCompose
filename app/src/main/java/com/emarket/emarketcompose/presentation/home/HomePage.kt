package com.emarket.emarketcompose.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.emarket.emarketcompose.R
import com.emarket.emarketcompose.components.header.EMarketHeader
import com.emarket.emarketcompose.components.search.EMarketSearch

@Composable
fun HomePage(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val homeState = viewModel.homeDataState.collectAsState()
    var searchText = remember {""}

    Column {
        EMarketHeader(headerTitle = "E-Market App")

        Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen._10dp)))

        Column(
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen._10dp),
                end = dimensionResource(id = R.dimen._10dp)
            )
        ) {
            EMarketSearch(
                onValueChange = {
                    searchText =it
                },
                onSearch = {
                    println(searchText)
                })
        }
    }
}