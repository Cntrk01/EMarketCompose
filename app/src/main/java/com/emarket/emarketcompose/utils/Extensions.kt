package com.emarket.emarketcompose.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.TextUnit

@Composable
fun dimensionResourceSp(id: Int): TextUnit {
    val density = LocalDensity.current
    val dimension = dimensionResource(id = id)
    return with(density) { dimension.toSp() }
}