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
import androidx.navigation.NavController
import com.emarket.emarketcompose.domain.repository.model.EMarketItem
import com.emarket.emarketcompose.navigations.NavigationState
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

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

//Bu kod ile her bottomnav itemine tıkladığımda recomposition işlemi yapmasının önüne geçtim.
//Bende her seferinde farklı sayfalarda tekrar tekrar istek atmak istemiyordum.Böylelikle sağladım
fun navigateToTap(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screen ->
            popUpTo(screen) {
                saveState = true
                //saveState = true:
                //popUpTo ile belirlenen noktaya kadar olan fragment/state'leri temizleme işlemi sırasında, bu fragmentlerin durumlarını (state) kaydetmeyi sağlar. Bu sayede fragmentlerin içeriği kaybolmaz
            }
            restoreState = true
            //Yönlendirme işlemi sonrasında eski fragment durumlarını geri yüklemeyi sağlar. Bu sayede kullanıcı navigasyon geçmişine geri döndüğünde fragmentlerin eski durumlarını görebilir.
            launchSingleTop = true
            //Hedef rotaya yönlendirme işlemi sırasında, eğer hedef rota zaten en üstteyse (mevcut olan), yeni bir instance oluşturmak yerine mevcut instance'ı kullanmayı sağlar. Bu, bir sayfanın tekrar tekrar açılmasını engeller.
        }
    }
}

fun navigateToDetails(navController: NavController, eMarketItem: EMarketItem) {
    navController.currentBackStackEntry?.savedStateHandle?.set("eMarketItem", eMarketItem)
    navController.navigate(
        route = NavigationState.Detail.route
    )
}

fun <T> handleFlowWithError(
    body: suspend () -> T, // İş mantığını çalıştıracak
    exceptionLamb: suspend (Exception) -> Unit
): Flow<Response<T>> = flow {
    try {
        val result = body() // İş mantığını çağırıp sonucu alıyoruz
        emit(Response.Success(data = result)) // Başarılı sonucu emit ediyoruz
    } catch (e: Exception) {
        when (e) {
            is IOException -> {
                emit(Response.Error("Network error occurred: Please check your internet connection."))
            }
            is HttpException -> {
                emit(Response.Error("HTTP error: ${e.code()} - ${e.message()}"))
            }
            is TimeoutCancellationException -> {
                emit(Response.Error("Request timed out: ${e.message}"))
            }
            else -> {
                emit(Response.Error("An unexpected error occurred: ${e.message}"))
            }
        }
    }
}