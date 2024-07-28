package com.emarket.emarketcompose.presentation.basket

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.emarket.emarketcompose.R
import com.emarket.emarketcompose.components.basket_item_row.EMarketBasketItemRow
import com.emarket.emarketcompose.components.button.EMarketButton
import com.emarket.emarketcompose.components.text.EMarketText
import com.emarket.emarketcompose.domain.repository.model.BasketItem

@Composable
fun BasketPage(
    modifier: Modifier = Modifier,
    basketViewModel: BasketViewModel = hiltViewModel()
) {
    basketViewModel.getBasket()

    var basketItemList by remember { mutableStateOf<List<BasketItem>>(emptyList()) }
    var totalPrice by remember { mutableDoubleStateOf(0.0) }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(basketViewModel) {
        val basketObserver = { items: List<BasketItem> ->
            basketItemList = items
        }

        val priceObserver = { price: Double ->
            totalPrice = price
        }

        basketViewModel.basket.observe(lifecycleOwner, basketObserver)
        basketViewModel.totalPrice.observe(lifecycleOwner, priceObserver)

        onDispose {
            basketViewModel.basket.removeObserver(basketObserver)
            basketViewModel.totalPrice.removeObserver(priceObserver)
        }
    }


    Column (
        modifier = Modifier.padding(dimensionResource(id = R.dimen._15dp))
    ){
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(basketItemList.size) { index ->

                val basketItem = basketItemList[index]

                EMarketBasketItemRow(
                    title = basketItem.productName,
                    price = basketItem.productPrice,
                    plusButton = {
                        basketViewModel.addBasketItem(basketItem = basketItem)
                    },
                    minusButton = {
                        basketViewModel.deleteBasketItem(productId = basketItem.productId)
                    },
                    totalOneItem = basketItem.productCount.toString()
                )
                Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen._10dp)))
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            EMarketText(
                text = "Price : $${totalPrice}",
                textColor = colorResource(id = R.color.primaryColor),
                maxLines = 1,
                minLines = 1,
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.weight(0.35f))

            EMarketButton(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                text = stringResource(R.string.complete_order),
                clickButton = {
                    basketViewModel.completeBasket()
                }
            )
        }
    }
}