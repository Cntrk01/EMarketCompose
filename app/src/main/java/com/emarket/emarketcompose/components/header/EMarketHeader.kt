package com.emarket.emarketcompose.components.header

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.emarket.emarketcompose.R
import com.emarket.emarketcompose.components.text.EMarketText
import com.emarket.emarketcompose.ui.theme.EMarketComposeTheme
import com.emarket.emarketcompose.utils.customClickable
import com.emarket.emarketcompose.utils.dimensionResourceSp

@Composable
fun EMarketHeader(
    modifier: Modifier = Modifier,
    headerTitle: String = "E-Market",
    headerPadding: Dp = dimensionResource(id = R.dimen._10dp),
    headerIcon : Int =R.drawable.baseline_arrow_back_24,
    headerType: HeaderType = HeaderType.SIMPLE,
    backClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(headerPadding),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (headerType == HeaderType.MULTI) {
            Icon(
                modifier = Modifier
                    .customClickable {
                        backClick()
                    }
                    .padding(start = dimensionResource(id = R.dimen._5dp))
                    .size(dimensionResource(id = R.dimen._45dp)),
                painter = painterResource(id = headerIcon),
                contentDescription = "Back",
                tint = colorResource(id = R.color.cardTitleColor)
            )
        }

        EMarketText(
            modifier = Modifier.padding(start = dimensionResource(id = R.dimen._15dp)),
            text = headerTitle,
            fontSize = dimensionResourceSp(id = R.dimen._20sp),
            textColor = colorResource(id = R.color.cardTitleColor),
            fontWeight = FontWeight.Bold
        )
    }
}
@Preview
@Composable
fun PreviewHeader() {
    EMarketComposeTheme {
        EMarketHeader(headerTitle = "Hello World", headerType = HeaderType.MULTI)
    }
}





