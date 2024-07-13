package com.emarket.emarketcompose.presentation.base_viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private var _addProducts = MutableLiveData<Boolean>()
    val listenerAddProducts: LiveData<Boolean> = _addProducts

    fun checkProducts(product: EMarketItem) = viewModelScope.launch(Dispatchers.IO) {
        val itemStatus = roomRepositoryImpl.checkExistProduct(product.itemId)
        if (itemStatus) {
            _addProducts.postValue(false)
            deleteProducts(productId = product)
        } else {
            _addProducts.postValue(true)
            addProduct(products = product)
        }
    }

    private fun addProduct(products: EMarketItem) = viewModelScope.launch(Dispatchers.IO) {
        roomRepositoryImpl.addProducts(products)
    }

    private fun deleteProducts(productId: EMarketItem) = viewModelScope.launch(Dispatchers.IO) {
        roomRepositoryImpl.deleteProducts(productId)
    }
}