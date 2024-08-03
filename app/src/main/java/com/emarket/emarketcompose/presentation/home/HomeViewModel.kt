package com.emarket.emarketcompose.presentation.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.emarket.emarketcompose.domain.repository.model.EMarketItem
import com.emarket.emarketcompose.domain.repository.model.FilterItem
import com.emarket.emarketcompose.domain.usecase.remote.DataListUseCase
import com.emarket.emarketcompose.domain.usecase.local.FavoriteUseCase
import com.emarket.emarketcompose.presentation.base_viewmodel.FavoriteBaseViewModel
import com.emarket.emarketcompose.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataListUseCase: DataListUseCase,
    private val favoriteUseCase: FavoriteUseCase,
) : FavoriteBaseViewModel(favoriteUseCase = favoriteUseCase) {

    private var maxDataListSize = 0
    private val _homeDataState = MutableStateFlow(HomeState())
    val homeDataState: StateFlow<HomeState> get() = _homeDataState

    private var pageIndex = 0
    private var cacheHomeDataList = listOf<EMarketItem>()
    private var filteredList = listOf<FilterItem>()

    private var _addProducts: MutableState<Map<String, Boolean>> = mutableStateOf(emptyMap())
    val listenerAddProducts: State<Map<String, Boolean>> = _addProducts

    private var isLoadingMoreData = false

    //Güncellemelerin senkronize edilmesi sağlanır, böylece aynı anda birden fazla erişim olmasını önler.
    //Kodda hangi durumlarda eş zamanlı erişim sorunlarıyla karşılaşabileceğini belirlemekle ilgilidir.
    // viewModelScope.launch içinde çalıştırılan fonksiyonlar zaten ana iş parçacığının dışında çalışır ve eğer _addProducts
    // durumu başka iş parçacıkları tarafından aynı anda değiştirilirse veri tutarsızlığı sorunlarına yol açabilir.
    // Ancak, Kotlin StateFlow ve MutableStateFlow zaten thread-safe yapılar olduğu için, ek bir Mutex kullanmak çoğu durumda gerekli olmayabilir.
    private val productStatusMutex = Mutex()

    init {
        getDataList(pageIndex = pageIndex)
    }

    private fun getDataList(pageIndex: Int) = viewModelScope.launch(Dispatchers.IO) {
        dataListUseCase
            .getData(
                pageIndex = pageIndex,
                listSize = { maxDataListSize = it },
                filterList = {
                    filteredList = it
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
        //withLock fonksiyonu, kilidi otomatik olarak serbest bırakır, böylece kilidi serbest bırakmayı unutma gibi hatalardan kaçınılır.
        //productStatusMutex.withLock {
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
        //}
    }

    fun updateProductStatus(product: EMarketItem) = viewModelScope.launch(Dispatchers.IO) {
        //productStatusMutex.withLock {
        val itemStatus = checkProduct(product.itemId)
        val updatedMap = _addProducts.value.toMutableMap()
        updatedMap[product.itemId] = itemStatus
        _addProducts.value = updatedMap
        //}
    }

    fun addToCardProduct(product: EMarketItem) = viewModelScope.launch (Dispatchers.IO){
        favoriteUseCase.addToCard(products = product)
    }
}