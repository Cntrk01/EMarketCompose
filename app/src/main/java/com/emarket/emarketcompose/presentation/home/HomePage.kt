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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.emarket.emarketcompose.R
import com.emarket.emarketcompose.components.button.EMarketButton
import com.emarket.emarketcompose.components.home_card.EMarketHomeCard
import com.emarket.emarketcompose.components.loading_status.EMarketLoading
import com.emarket.emarketcompose.components.search.EMarketSearch
import com.emarket.emarketcompose.components.text.EMarketText
import com.emarket.emarketcompose.domain.repository.model.EMarketItem
import com.emarket.emarketcompose.domain.repository.model.FilterItem
import com.emarket.emarketcompose.presentation.base_viewmodel.EMarketRoomViewModel
import com.emarket.emarketcompose.utils.getScreenWidthInDp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomePage(
    homeState: StateFlow<HomeState>,
    viewModel: HomeViewModel,
    clickDetail: (EMarketItem) -> Unit,
    clickFilter: (List<FilterItem>) -> Unit,
    roomViewModel: EMarketRoomViewModel = hiltViewModel()
) {
    var firstLoadings = remember { true }
    val checkFirstLoading = remember { derivedStateOf { firstLoadings } }
    var bottomLoading = remember { true }
    val isSearching = remember { false }
    var imageStatus = remember { false }
    var dataState by remember { mutableStateOf(HomeState()) }

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
//        roomViewModel.listenerAddProducts.observe(LocalLifecycleOwner.current) {
//            imageStatus = it
//        }
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
                        onValueChange = {
                            viewModel.searchItem(it)
                        },
                        onSearch = {}
                    )

                    EMarketButton(
                        modifier = Modifier.align(Alignment.End),
                        text = "Filters",
                        clickButton = {
                            clickFilter(
                                homeDataList?.map { it.filterItem } ?: emptyList()
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
                        bottomLoading = false

                        Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen._10dp)))

                        //LazyVerticalStaggeredGrid de kullanabilirim.
                        //Fakat ben düzenli gözükmesini istiyorum ondan bunu kullandım.

                        HomeItemLayout(
                            homeDataList = homeDataList!!,
                            homeDataListSize = homeDataListSize,
                            viewModel = viewModel,
                            isBottomLoad = bottomLoading,
                            clickDetail = {
                                clickDetail(it)
                            },
                            clickFavorite = {
                                roomViewModel.checkProducts(product = it)
                            },
                            roomViewModel = roomViewModel
                        )
                    }

                    homeSearchList?.isNotEmpty() == true -> {
                        bottomLoading = false

                        HomeItemLayout(
                            homeDataList = homeSearchList!!,
                            homeDataListSize = homeSearchList!!.size,
                            viewModel = viewModel,
                            isBottomLoad = bottomLoading,
                            clickDetail = {
                                clickDetail(it)
                            },
                            clickFavorite = {},
                            roomViewModel = roomViewModel
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
    isBottomLoad: Boolean,
    clickDetail: (EMarketItem) -> Unit,
    clickFavorite: (EMarketItem) -> Unit,
    roomViewModel: EMarketRoomViewModel,
) {
    var bottomLoading = remember { isBottomLoad }
    val listenerProducts = roomViewModel.listenerAddProducts.value

    LazyVerticalGrid(
        columns = GridCells.Adaptive(getScreenWidthInDp() / 3),
        content = {
            items(
                count = homeDataList.size,
                key = { index -> homeDataList[index].itemId },
                itemContent =
                { index ->
                    if (index == homeDataList.size - 1 && homeDataListSize != homeDataList.size) {
                        viewModel.loadMoreDataList()
                        bottomLoading = true
                    }

                    roomViewModel.updateProductStatus(product = homeDataList[index])

                    val productStatus = listenerProducts[homeDataList[index].itemId] ?: false

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
                            clickFavorite(
                                homeDataList[index]
                            )
                        },
                        clickDetail = {
                            clickDetail(homeDataList[index])
                        },
                        isShowStar = productStatus
                    )
                }
            )

            item(span = { GridItemSpan(maxLineSpan) }) {
                if (bottomLoading) {
                    EMarketLoading(
                        modifier = Modifier
                            .padding(bottom = dimensionResource(id = R.dimen._10dp))
                    )
                }
            }
        }
    )
}