package com.emarket.emarketcompose.presentation.basket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emarket.emarketcompose.domain.repository.model.BasketItem
import com.emarket.emarketcompose.domain.usecase.local.BasketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val basketUseCase: BasketUseCase
) : ViewModel() {

    private var _basket = MutableLiveData<List<BasketItem>>()
    val basket: LiveData<List<BasketItem>> get() = _basket

    private var _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<Double> get() = _totalPrice

    fun getBasket() = viewModelScope.launch(Dispatchers.IO) {
        val getBasketList = basketUseCase.getAllBasket()

        withContext(Dispatchers.Main) {
            _basket.value = getBasketList
            calculateTotalPrice(getBasketList)
        }
    }

    private fun calculateTotalPrice(basketItems: List<BasketItem>) {
        val total = basketItems.sumOf { it.productCount * it.productPrice.toDouble() }
        _totalPrice.value = total
    }

    fun deleteBasketItem(productId : String) = viewModelScope.launch (Dispatchers.IO){
        basketUseCase.deleteProductFromBasket(productId = productId)
        getBasket()
    }

    fun addBasketItem(basketItem: BasketItem) = viewModelScope.launch (Dispatchers.IO){
        basketUseCase.addProductInBasket(product = basketItem)
        getBasket()
    }

    fun completeBasket() = viewModelScope.launch (Dispatchers.IO){
        basketUseCase.completeBasket()
        getBasket()
    }
}