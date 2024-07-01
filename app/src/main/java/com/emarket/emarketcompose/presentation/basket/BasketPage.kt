package com.emarket.emarketcompose.presentation.basket

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.emarket.emarketcompose.components.filter.EMarketFilter
import com.emarket.emarketcompose.components.filter.FilterType
import com.emarket.emarketcompose.components.text.EMarketText

@Composable
fun BasketPage(modifier: Modifier = Modifier) {
    Column {
        EMarketText(text = "HELLO WORLDD")

        EMarketFilter(
            filterList = listOf("123","HELOO","GRR"),
            checkBoxList = {
                println(it)
        })

        Spacer(modifier = Modifier.padding(20.dp))

        EMarketFilter(
            filterList = listOf("123","HELOO","GRR"),
            filterType = FilterType.RADIO,
            radioItem = {
            println(
                it
            )
        })


    }
}