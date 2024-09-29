package com.emarket.emarketcompose.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.emarket.emarketcompose.domain.repository.model.EMarketItem
import com.emarket.emarketcompose.domain.usecase.local.FavoriteUseCase
import com.emarket.emarketcompose.domain.usecase.remote.DataListUseCase
import com.emarket.emarketcompose.presentation.base_viewmodel.FavoriteBaseViewModel
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
    private val favoriteUseCase: FavoriteUseCase,
) : FavoriteBaseViewModel(favoriteUseCase = favoriteUseCase) {

    private val _homeDataState = MutableStateFlow(HomeState())
    val homeDataState: StateFlow<HomeState> get() = _homeDataState

    private var cacheHomeDataList = listOf<EMarketItem>()

    private var _addProducts = mutableStateOf(emptyMap<String, Boolean>())
    val listenerAddProducts: State<Map<String, Boolean>> = _addProducts

    private var hasMoreData = true

    init {
        getDataList()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.LoadData -> {
                println("Calısıyor")
                loadMoreDataList()
            }

            is HomeEvent.SearchItem -> searchItem(query = event.query)

            is HomeEvent.FilterItems -> {
                viewModelScope.launch(Dispatchers.IO) {
                    //updateFilters(event.filters)
                    //_events.emit(event)
                }
            }

            is HomeEvent.ClickDetail -> {
                viewModelScope.launch {
                    //_events.emit(event)
                }
            }

            is HomeEvent.AddToCart -> addToCardProduct(product = event.item)

            is HomeEvent.CheckProduct -> checkProducts(product = event.item)

            is HomeEvent.UpdateProductStatus -> updateProductStatus(product = event.item)
        }
    }

    private fun getDataList() = viewModelScope.launch(Dispatchers.IO) {

        dataListUseCase.getData().collectLatest { response ->
                when (response) {
                    is Response.Loading -> _homeDataState.update { it.copy(homeLoading = true) }
                    is Response.Error -> _homeDataState.update {
                        it.copy(
                            homeLoading = false,
                            homeError = response.message.toString(),
                        )
                    }

                    is Response.Success -> {
                        val newData = response.data.orEmpty()

                        if (newData.isEmpty()) {
                            // Eğer yeni veri gelmiyorsa daha fazla istek atmayı durdur
                            hasMoreData = false
                        } else {
                            // Yeni veri varsa listeye ekle ve sayfayı güncelle
                            cacheHomeDataList += newData

                            _homeDataState.update {
                                it.copy(
                                    homeLoading = false,
                                    homeError = "",
                                    homeDataList = cacheHomeDataList
                                )
                            }
                        }
                    }
                    //Bu şekilde bir kullanım sağladığımda yeni nesne üretim eşitlediği için ekran sürekli
                    //recompositiona ugruyordu fakat ben var olan statemi update ederek yanlızca olan değişiklikleri aktarmış oldum !!
                    //_homeDataState.value=HomeState(
                    //homeError = "",
                    //homeLoading = false,
                    //homeDataList = homeDataList
                    //)
                }
            }
    }

    private fun loadMoreDataList() {
        if (!hasMoreData) return // Eğer istek yapılmışsa veya daha fazla veri yoksa çık
        getDataList()
    }

    private fun searchItem(query: String) = viewModelScope.launch(Dispatchers.IO) {
        if (query.isEmpty()){
            _homeDataState.update {
                it.copy(
                    homeLoading = false,
                    homeError = "",
                    homeSearchList = emptyList(),
                    isSearch = false
                )
            }
        }

        dataListUseCase.searchData(query = query).collect { response ->
            when (response) {
                is Response.Loading -> _homeDataState.update { it.copy(isSearch = true,homeLoading = true) }

                is Response.Error -> _homeDataState.update {
                    it.copy(
                        homeLoading = false,
                        homeError = response.message.toString(),
                        isSearch = true
                    )
                }

                is Response.Success -> {
                    _homeDataState.update {
                        it.copy(
                            homeLoading = false,
                            homeError = "",
                            homeSearchList = response.data,
                            isSearch = true
                        )
                    }
                }
            }
        }
    }

    private fun checkProducts(product: EMarketItem) = viewModelScope.launch(Dispatchers.IO) {
        val isFavorite = checkProduct(product.itemId)
        _addProducts.value = _addProducts.value.toMutableMap().apply {
            this[product.itemId] = !isFavorite
        }
        if (isFavorite) removeFromFavorite(product) else addToFavorite(product)
    }

    private fun updateProductStatus(product: EMarketItem) = viewModelScope.launch(Dispatchers.IO) {
        val itemStatus = checkProduct(product.itemId)
        _addProducts.value =
            _addProducts.value.toMutableMap().apply { this[product.itemId] = itemStatus }
    }

    private fun addToCardProduct(product: EMarketItem) = viewModelScope.launch(Dispatchers.IO) {
        favoriteUseCase.addToCard(products = product)
    }
}