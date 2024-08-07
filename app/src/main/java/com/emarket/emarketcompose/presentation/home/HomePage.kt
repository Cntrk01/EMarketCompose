package com.emarket.emarketcompose.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.emarket.emarketcompose.R
import com.emarket.emarketcompose.components.button.EMarketButton
import com.emarket.emarketcompose.components.home_card.EMarketHomeCard
import com.emarket.emarketcompose.components.loading_status.EMarketLoading
import com.emarket.emarketcompose.components.search.EMarketSearch
import com.emarket.emarketcompose.components.text.EMarketText
import com.emarket.emarketcompose.domain.repository.model.EMarketItem
import com.emarket.emarketcompose.domain.repository.model.FilterItem
import com.emarket.emarketcompose.utils.getScreenWidthInDp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomePage(
    viewModel: HomeViewModel = hiltViewModel(),
    clickDetail: (EMarketItem) -> Unit,
    clickFilter: (List<FilterItem>) -> Unit,
) {
    var firstLoadings = remember { true }
    val checkFirstLoading = remember { derivedStateOf { firstLoadings } }
    val isSearching = remember { false }
    var dataState by remember { mutableStateOf(HomeState()) }
    var searchText by rememberSaveable { mutableStateOf("") }
    val homeState = viewModel.homeDataState

    DisposableEffect(homeState) {
        val job = viewModel.viewModelScope.launch(Dispatchers.IO) {
            homeState.collect { value ->
                dataState = value
            }
        }
        onDispose {
            job.cancel()
        }
    }

    Column {
        Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen._10dp)))

        Column(
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen._10dp),
                end = dimensionResource(id = R.dimen._10dp)
            )
        ) {

            dataState.apply {
                if (!homeLoading) {
                    EMarketSearch(
                        text = searchText,
                        onValueChange = {
                            searchText = it
                            viewModel.onEvent(HomeEvent.SearchItem(query = it))
                        },
                        onSearch = {
                            viewModel.onEvent(HomeEvent.SearchItem(query = searchText))
                        }
                    )

                    EMarketButton(
                        modifier = Modifier.align(Alignment.End),
                        text = "Filters",
                        clickButton = {
                            clickFilter(
                                filterList?.filter { (it.model.isNotEmpty() && it.brand.isNotEmpty()) } ?: emptyList()
                            )
                        }
                    )
                }
                when {
                    homeLoading && checkFirstLoading.value -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            EMarketLoading()
                        }
                        firstLoadings = false
                    }

                    homeError.isNotEmpty() -> {
                        EMarketText(text = homeError)
                    }

                    homeDataList?.isNotEmpty() == true && !isSearching -> {

                        Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen._2dp)))

                        //LazyVerticalStaggeredGrid de kullanabilirim.
                        //Fakat ben düzenli gözükmesini istiyorum ondan bunu kullandım.
                        HomeItemLayout(
                            homeDataList = homeDataList!!,
                            homeDataListSize = homeDataListSize,
                            viewModel = viewModel,
                            clickDetail = {
                                clickDetail(it)
                            },
                        )
                    }

                    homeSearchList?.isNotEmpty() == true -> {

                        HomeItemLayout(
                            homeDataList = homeSearchList!!,
                            homeDataListSize = homeSearchList!!.size,
                            viewModel = viewModel,
                            clickDetail = {
                                clickDetail(it)
                            },
                        )
                    }
                }
            }
        }
    }
}

//@Composable
//fun <T> LiveData<T>.observeAsState(initial: T? = null): State<T?> {
//    val lifecycleOwner = LocalLifecycleOwner.current
//    val state = remember { mutableStateOf(initial) }
//    DisposableEffect(this, lifecycleOwner) {
//        val observer = Observer<T> { value -> state.value = value }
//        observe(lifecycleOwner, observer)
//        onDispose { removeObserver(observer) }
//    }
//    return state
//}

@Composable
fun HomeItemLayout(
    homeDataList: List<EMarketItem>,
    homeDataListSize: Int,
    viewModel: HomeViewModel,
    clickDetail: (EMarketItem) -> Unit,
) {
    val listenerProducts = viewModel.listenerAddProducts.value

    LazyVerticalGrid(
        columns = GridCells.Adaptive(getScreenWidthInDp() / 3),
        content = {
            items(
                count = homeDataList.size,
                itemContent = { index ->
                    //homeDataListSize != homeDataList.size eşit olmadığı sürece load işlemi yapcaz. Eşit olduktan sonra tekrar yaparsa ekran boş gözüküyor
                    if (index == homeDataList.size - 1 && homeDataListSize != homeDataList.size) {
                        viewModel.onEvent(HomeEvent.LoadData)
                    }

                    viewModel.onEvent(event = HomeEvent.UpdateProductStatus(item = homeDataList[index]))

                    val productStatus = listenerProducts[homeDataList[index].itemId] ?: false

                    EMarketHomeCard(
                        modifier = Modifier.padding(dimensionResource(id = R.dimen._5dp)),
                        image = homeDataList[index].image,
                        price = homeDataList[index].price,
                        description = homeDataList[index].name,
                        clickAddToCardButton = {
                            viewModel.onEvent(event = HomeEvent.AddToCart(item = homeDataList[index]))
                        },
                        clickFavorite = {
                            viewModel.onEvent(event = HomeEvent.CheckProduct(item = homeDataList[index]))
                        },
                        clickDetail = {
                            clickDetail(homeDataList[index])
                        },
                        isShowStar = productStatus
                    )
                }
            )

            item(span = { GridItemSpan(maxLineSpan) }) {
                if (homeDataList.size < homeDataListSize) {
                    EMarketLoading(modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen._10dp)))
                }
            }
        }
    )
}