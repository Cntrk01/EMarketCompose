package com.emarket.emarketcompose.presentation.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emarket.emarketcompose.R
import com.emarket.emarketcompose.components.filter.EMarketFilter
import com.emarket.emarketcompose.components.filter.FilterType
import com.emarket.emarketcompose.components.header.EMarketHeader
import com.emarket.emarketcompose.components.header.HeaderType
import com.emarket.emarketcompose.components.search.EMarketSearch
import com.emarket.emarketcompose.components.text.EMarketText
import com.emarket.emarketcompose.ui.theme.EMarketComposeTheme
import com.emarket.emarketcompose.utils.Constants

@Composable
fun FilterPage(
    modifier: Modifier = Modifier,
    brandList: List<String>,
    modelList: List<String>,
) {
    Column(
        modifier = modifier.background(color = colorResource(id = R.color.background))
    ) {
        //Gölge
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Black.copy(alpha = 0.2f), Color.Transparent)
                    )
                )
        )

        Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen._10dp)))

        LazyColumn (
            modifier = Modifier.weight(1f)
        ){
            item {
                SortByFunc()
            }
        }

        Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen._10dp)))

        Divider(
            color = Color.Gray,
            thickness = dimensionResource(id = R.dimen._2dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen._10dp),
                    end = dimensionResource(id = R.dimen._10dp)
                )
        )

        Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen._10dp)))

        LazyColumn (
            modifier = Modifier.weight(1f)
        ){
            item {
                BrandFunc(brandList = brandList, title = "Brand")
            }
        }
        Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen._15dp)))

        Divider(
            color = Color.Gray,
            thickness = dimensionResource(id = R.dimen._2dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen._10dp),
                    end = dimensionResource(id = R.dimen._10dp)
                )
        )

        Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen._15dp)))

        LazyColumn (
            modifier = Modifier.weight(1f)
        ){
            item {
                //modelList
                BrandFunc(brandList = modelList, title = "Model")
            }
        }
    }
}

@Composable
fun SortByFunc() {
    EMarketText(
        modifier = Modifier
            .padding(start = dimensionResource(id = R.dimen._24dp)),
        text = "Sort By",
        textColor = colorResource(id = R.color.cardTitleColor)
    )

    Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen._10dp)))

    EMarketFilter(
        modifier = Modifier.padding(start = dimensionResource(id = R.dimen._35dp)),
        filterList = Constants.SORT_BY_LIST,
        filterType = FilterType.RADIO
    )
}

@Composable
fun BrandFunc(
    brandList: List<String>,
    title: String
) {
    var searchItem by remember { mutableStateOf("") }

    EMarketText(
        modifier = Modifier
            .padding(start = dimensionResource(id = R.dimen._24dp)),
        text = title,
        textColor = colorResource(id = R.color.cardTitleColor)
    )

    Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen._15dp)))

    EMarketSearch(
        modifier = Modifier.padding(
            start = dimensionResource(id = R.dimen._24dp),
            end = dimensionResource(id = R.dimen._24dp)
        ),
        onValueChange = {
            searchItem = it
        },
        onSearch = {

        }
    )

    EMarketFilter(
        modifier = Modifier.padding(start = dimensionResource(id = R.dimen._35dp)),
        filterList = brandList,
    )
}

@Preview
@Composable
fun PreviewFilterPage() {
    EMarketComposeTheme {
        FilterPage(
            brandList = listOf("Apple", "Samsung", "Huawei", "Xioami"),
            modelList = listOf("iPhone", "Galaxy", "P40"),
        )
    }
}