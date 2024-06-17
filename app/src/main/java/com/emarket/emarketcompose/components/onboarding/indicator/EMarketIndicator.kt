package com.emarket.emarketcompose.components.onboarding.indicator

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.emarket.emarketcompose.R

@Composable
fun EMarketIndicator(
    modifier: Modifier = Modifier,
    pageSize: Int,
    selectedPage: Int,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageSize) { index ->

            val size by animateDpAsState(
                targetValue = if (index == selectedPage)
                                   dimensionResource(id = R.dimen._20dp)
                              else dimensionResource(id = R.dimen._10dp),
                label = ""
            )

            Box(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen._5dp))
                    .size(size)
                    .clip(CircleShape)
                    .background(color = colorResource(id = R.color.primaryColor)),
                )
        }
    }
}

@Preview
@Composable
fun PreviewEMarketIndicator() {
    EMarketIndicator(pageSize = 5, selectedPage = 1)
}