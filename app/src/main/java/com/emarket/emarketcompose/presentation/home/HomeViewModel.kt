package com.emarket.emarketcompose.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emarket.emarketcompose.domain.repository.model.EMarketItem
import com.emarket.emarketcompose.domain.usecase.data_list.DataListUseCase
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
    private val dataListUseCase: DataListUseCase
) : ViewModel() {

    private val _homeDataState = MutableStateFlow(HomeState())
    val homeDataState: StateFlow<HomeState> get() = _homeDataState

    private var pageIndex = 1
    private var homeDataList = emptyList<EMarketItem>()

    init {
        getDataList(pageIndex = pageIndex)
    }

    private fun getDataList(pageIndex: Int) = viewModelScope.launch(Dispatchers.IO) {
        dataListUseCase.getData(pageIndex = pageIndex)
            .collectLatest { response ->
                when (response) {
                    is Response.Loading -> {
                        _homeDataState.update { state ->
                            state.copy(loading = true)
                        }
                    }

                    is Response.Error -> {
                        _homeDataState.update { state ->
                            state.copy(
                                error = response.message.toString(),
                                loading = false
                            )
                        }
                    }

                    is Response.Success -> {
                        homeDataList += response.data ?: emptyList()

                        _homeDataState.update { state ->
                            state.copy(
                                error = "",
                                loading = false,
                                dataList = homeDataList
                            )
                        }
                    }
                }
            }
    }

    fun loadMoreDataList() {
        pageIndex += 1
        getDataList(pageIndex = pageIndex)
    }
}