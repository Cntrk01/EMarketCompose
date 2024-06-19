package com.emarket.emarketcompose.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.emarket.emarketcompose.R
import com.emarket.emarketcompose.components.header.EMarketHeader
import com.emarket.emarketcompose.components.home_card.EMarketHomeCard
import com.emarket.emarketcompose.components.loading_status.EMarketLoading
import com.emarket.emarketcompose.components.search.EMarketSearch
import com.emarket.emarketcompose.components.text.EMarketText

@Composable
fun HomePage(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val homeState = viewModel.homeDataState.collectAsState(HomeState())
    var searchText = remember { "" }

    Column {
        EMarketHeader(headerTitle = "E-Market App")

        Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen._10dp)))

        Column(
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen._10dp),
                end = dimensionResource(id = R.dimen._10dp)
            )
        ) {
            homeState.value.apply {
                if (homeLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        EMarketLoading()
                    }
                }
                if (homeError.isNotEmpty()) {
                    EMarketText(text = homeError)

                }
                if (homeDataList?.isNotEmpty() == true) {
                    EMarketSearch(
                        onValueChange = {
                            searchText = it
                        },
                        onSearch = {
                            println(searchText)
                        }
                    )

                    Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen._10dp)))

                    //LazyVerticalStaggeredGrid de kullanabilirim.Fakat ben düzenli gözükmesini istiyorum ondan bunu kullandım.
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        content = {
                            items(homeDataList.size) { index ->
                                //Apiden homeDataListSize toplam api sonucunun int değerini dönüyor
                                //homeDataList.size da elimde var olan datayı dönüyor.En son datayı çekince bu değerler birbirine eşit olacağı için if bloğu çalışmıyor böylelikle apiye artık istek atılmıyor
                                if (index == homeDataList.size-1 && homeDataListSize != homeDataList.size){
                                    viewModel.loadMoreDataList()
                                }

                                EMarketHomeCard(
                                    modifier = Modifier
                                        .padding(dimensionResource(id = R.dimen._5dp)),
                                    image = homeDataList[index].image,
                                    price = homeDataList[index].price,
                                    description = homeDataList[index].name,
                                    clickButton = {
                                        // Card üzerindeki butona tıklama işlemleri
                                    },
                                    clickFavorite = {
                                        // Favoriye ekleme/çıkarma işlemleri
                                        // Veritabanına yazma işlemi yapılacak
                                        false
                                    },
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}