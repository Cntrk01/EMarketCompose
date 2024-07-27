package com.emarket.emarketcompose.presentation.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emarket.emarketcompose.domain.repository.model.EMarketItem
import com.emarket.emarketcompose.domain.repository.model.FilterItem
import com.emarket.emarketcompose.domain.usecase.data_list.DataListUseCase
import com.emarket.emarketcompose.domain.usecase.db.FavoriteUseCase
import com.emarket.emarketcompose.presentation.base_viewmodel.FavoriteBaseViewModel
import com.emarket.emarketcompose.presentation.favorite.FavoriteViewModel
import com.emarket.emarketcompose.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataListUseCase: DataListUseCase,
    private val favoriteUseCase: FavoriteUseCase
) : FavoriteBaseViewModel(favoriteUseCase = favoriteUseCase) {

    private var maxDataListSize = 0
    private val _homeDataState = MutableStateFlow(HomeState())
    val homeDataState: StateFlow<HomeState> get() = _homeDataState

    private var pageIndex = 0
    private var cacheHomeDataList = listOf<EMarketItem>()
    private var filteredList = mutableListOf<FilterItem>()

    private var _addProducts: MutableState<Map<String, Boolean>> = mutableStateOf(emptyMap())
    val listenerAddProducts: State<Map<String, Boolean>> = _addProducts

    private var isLoadingMoreData = false

    init {
        getDataList(pageIndex = pageIndex)
    }

    private fun getDataList(pageIndex: Int) = viewModelScope.launch(Dispatchers.IO) {
        dataListUseCase
            .getData(
                pageIndex = pageIndex,
                listSize = { maxDataListSize = it },
                filterList = {
                    filteredList = it.toMutableList()
                }
            ).collect { response ->
                when (response) {

                    is Response.Loading -> {
                        _homeDataState.update { state ->
                            state.copy(
                                homeLoading = true,
                            )
                        }
                    }

                    is Response.Error -> {
                        _homeDataState.update { state ->
                            state.copy(
                                homeLoading = false,
                                homeError = response.message.toString()
                            )
                        }
                    }

                    is Response.Success -> {
                        cacheHomeDataList += response.data ?: emptyList()

                        _homeDataState.update { state ->
                            state.copy(
                                homeLoading = false,
                                homeError = "",
                                homeDataList = cacheHomeDataList,
                                homeDataListSize = maxDataListSize,
                                filterList = filteredList
                            )
                        }
                        isLoadingMoreData = false
                        println(cacheHomeDataList.size)

//Bu şekilde bir kullanım sağladığımda yeni nesne üretim eşitlediği için ekran sürekli
//recompositiona ugruyordu fakat ben var olan statemi update ederek yanlızca olan değişiklikleri aktarmış oldum !!
//                        _homeDataState.value=HomeState(
//                            homeError = "",
//                            homeLoading = false,
//                            homeDataList = homeDataList
//                        )
                    }
                }
            }
    }

    //isLoadingMoreData ile loading işlemi yaptığım kısımdan birden çok istek geldiği için burada tek 1 kez çalışmasını sağlayan bir yapı kurdum
    fun loadMoreDataList() {
        if (isLoadingMoreData) return
        isLoadingMoreData = true
        pageIndex += 1
        getDataList(pageIndex = pageIndex)
    }

    fun searchItem(query: String) {
        _homeDataState.update { currentState ->
            if (query.isEmpty()) {
                currentState.copy(
                    homeDataList = cacheHomeDataList,
                    homeSearchList = null
                )
            } else {

                //if (cacheHomeDataList.isEmpty()) {
                //                    _homeDataState.value.homeDataList?.let { cacheHomeDataList = it }
                //                }

                val searchItem = cacheHomeDataList.filter { item ->
                    item.name.contains(query, ignoreCase = true) ||
                            item.description.contains(query, ignoreCase = true)
                }

                currentState.copy(
                    homeSearchList = searchItem,
                    homeDataList = null
                )
            }
        }
    }

    fun checkProducts(product: EMarketItem) = viewModelScope.launch(Dispatchers.IO) {
        val itemStatus = checkProduct(product.itemId)
        val updatedMap = _addProducts.value.toMutableMap()
        if (itemStatus) {
            updatedMap[product.itemId] = false
            removeFromFavorite(product)
        } else {
            updatedMap[product.itemId] = true
            addToFavorite(product)
        }
        _addProducts.value = updatedMap
    }

    fun updateProductStatus(product: EMarketItem) = viewModelScope.launch(Dispatchers.IO) {
        val itemStatus = checkProduct(product.itemId)
        val updatedMap = _addProducts.value.toMutableMap()
        updatedMap[product.itemId] = itemStatus
        _addProducts.value = updatedMap
    }
}