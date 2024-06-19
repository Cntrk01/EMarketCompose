package com.emarket.emarketcompose.presentation.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emarket.emarketcompose.domain.repository.model.EMarketItem
import com.emarket.emarketcompose.domain.usecase.data_list.DataListUseCase
import com.emarket.emarketcompose.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataListUseCase: DataListUseCase
) : ViewModel() {
    private var maxDataListSize = 0
    private val _homeDataState = MutableStateFlow(HomeState())
    val homeDataState: StateFlow<HomeState> get() = _homeDataState

    private var pageIndex = 0
    private var homeDataList = mutableStateListOf<EMarketItem>()

    init {
        getDataList(pageIndex = pageIndex)
    }

    private fun getDataList(pageIndex: Int) = viewModelScope.launch(Dispatchers.IO) {
        dataListUseCase
            .getData(
                pageIndex = pageIndex,
                listSize = { maxDataListSize = it }
            )
            .collect { response ->
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
                        homeDataList += response.data ?: emptyList()

                        _homeDataState.update { state ->
                            state.copy(
                                homeLoading = false,
                                homeError = "",
                                homeDataList = homeDataList,
                                homeDataListSize = maxDataListSize
                            )
                        }
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

    fun loadMoreDataList() {
        pageIndex += 1
        getDataList(pageIndex = pageIndex)
    }
}