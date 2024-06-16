package com.emarket.emarketcompose.components.basket_item_row

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.emarket.emarketcompose.R
import com.emarket.emarketcompose.components.button.EMarketButton
import com.emarket.emarketcompose.components.text.EMarketText
import com.emarket.emarketcompose.utils.dimensionResourceSp

@Composable
fun EMarketBasketItemRow(
    modifier: Modifier = Modifier,
    title: String,
    price: String,
    plusButton: () -> Unit,
    minusButton: () -> Unit,
    totalOneItem: String
) {
    Row(
        modifier = modifier
            .background(color = colorResource(id = R.color.basketItemRowColor)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = dimensionResource(id = R.dimen._10dp)),
            horizontalAlignment = Alignment.Start
        ) {
            EMarketText(
                text = title,
                textColor = colorResource(id = R.color.cardTitleColor),
                fontSize = dimensionResourceSp(R.dimen._16sp)
            )
            EMarketText(
                text = "$price$",
                textColor = colorResource(id = R.color.primaryColor),
                fontSize = dimensionResourceSp(R.dimen._14sp)
            )
        }

        Row(
            modifier = Modifier
                .weight(1f)
                .padding(end = dimensionResource(id = R.dimen._10dp)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            EMarketButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(dimensionResource(id = R.dimen._2dp)),
                text = "-",
                clickButton = { minusButton() },
                color = colorResource(id = R.color.cardColor)
            )

            EMarketButton(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight(),
                text = totalOneItem,
                clickButton = { /*TODO*/ })

            EMarketButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(dimensionResource(id = R.dimen._2dp)),
                text = "+",
                clickButton = { plusButton() },
                color = colorResource(id =  R.color.cardColor)
            )
        }
    }
}

@Preview
@Composable
fun PreviewEMarketBasketItemRow() {
    EMarketBasketItemRow(
        title = "Samsung S22",
        price = "20.000",
        plusButton = { /*TODO*/ },
        minusButton = { /*TODO*/ },
        totalOneItem = "5"
    )
}