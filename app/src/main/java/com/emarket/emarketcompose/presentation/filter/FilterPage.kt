package com.emarket.emarketcompose.presentation.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.emarket.emarketcompose.R
import com.emarket.emarketcompose.components.header.EMarketHeader
import com.emarket.emarketcompose.components.header.HeaderType
import com.emarket.emarketcompose.ui.theme.EMarketComposeTheme

@Composable
fun FilterPage(
    modifier: Modifier = Modifier,
    backClick: () -> Unit,
) {
    Column(
        modifier = modifier
    ){
        EMarketHeader(
            modifier = Modifier
                .background(color = colorResource(id = R.color.background))
            ,
            headerPadding= dimensionResource(id = R.dimen._15dp),
            headerType=HeaderType.MULTI,
            headerTitle = "Filter",
            headerIcon = R.drawable.baseline_close_24,
            backClick = {
                backClick()
            }
        )
    }
}

@Preview
@Composable
fun PreviewFilterPage(){
    EMarketComposeTheme {
        FilterPage(
            backClick = {

            }
        )
    }
}