package com.emarket.emarketcompose.presentation.base_viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emarket.emarketcompose.data.repository.local.EMarketRoomRepositoryImpl
import com.emarket.emarketcompose.domain.repository.model.EMarketItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EMarketRoomViewModel @Inject constructor(
    private val roomRepositoryImpl: EMarketRoomRepositoryImpl
) : ViewModel() {

    private var _addProducts: MutableState<Map<String, Boolean>> = mutableStateOf(emptyMap())
    val listenerAddProducts: State<Map<String, Boolean>> = _addProducts

    fun checkProducts(product: EMarketItem) = viewModelScope.launch(Dispatchers.IO) {
        val itemStatus = roomRepositoryImpl.checkExistProduct(product.itemId)
        val updatedMap = _addProducts.value.toMutableMap()
        if (itemStatus) {
            updatedMap[product.itemId] = false
            deleteProducts(product)
        } else {
            updatedMap[product.itemId] = true
            addProduct(product)
        }
        _addProducts.value = updatedMap
    }

    fun updateProductStatus(product: EMarketItem) = viewModelScope.launch(Dispatchers.IO) {
        val itemStatus = roomRepositoryImpl.checkExistProduct(product.itemId)
        val updatedMap = _addProducts.value.toMutableMap()
        updatedMap[product.itemId] = itemStatus
        _addProducts.value = updatedMap
    }

    private fun addProduct(product: EMarketItem) = viewModelScope.launch(Dispatchers.IO) {
        roomRepositoryImpl.addProducts(product)
    }

    private fun deleteProducts(product: EMarketItem) = viewModelScope.launch(Dispatchers.IO) {
        roomRepositoryImpl.deleteProducts(product)
    }
}
