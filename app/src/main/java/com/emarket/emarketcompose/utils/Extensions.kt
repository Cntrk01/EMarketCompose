package com.emarket.emarketcompose.utils

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

//Böyle tanımlama sebebim bir çok yerde tıklama işlemi yapıyorum.
// Columna tıkladığım anda background beliriyor.Bunun görünmesini
// istemediğim için default olarak transparent yapıyorum
//Bu duruma ripple effect deniyor indication değişkenini null yaparak sağlıyorum
fun Modifier.customClickable(
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    indication: Indication? = null,
    onClick: () -> Unit
) : Modifier {

    return this.clickable(
        interactionSource = interactionSource,
        indication = indication
    ) {
        onClick()
    }
}