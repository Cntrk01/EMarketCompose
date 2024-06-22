package com.emarket.emarketcompose.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.emarket.emarketcompose.components.header.EMarketHeader
import com.emarket.emarketcompose.components.header.HeaderType
import com.emarket.emarketcompose.components.text.EMarketText

@Composable
fun DetailPage(
    modifier: Modifier = Modifier,
) {
    Column (
        modifier=modifier
    ){
        EMarketText(text = "Hello Detail")
    }
}