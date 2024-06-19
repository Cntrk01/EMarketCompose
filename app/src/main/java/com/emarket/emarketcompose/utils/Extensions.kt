package com.emarket.emarketcompose.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun dimensionResourceSp(id: Int): TextUnit {
    val density = LocalDensity.current
    val dimension = dimensionResource(id = id)
    return with(density) { dimension.toSp() }
}

@Composable
fun getScreenWidthInDp(): Dp {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    return screenWidthDp
}