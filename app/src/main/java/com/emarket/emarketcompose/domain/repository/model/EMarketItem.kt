package com.emarket.emarketcompose.domain.repository.model

import com.emarket.emarketcompose.data.dto.EMarketResponseItem
import java.io.Serializable

data class EMarketItem(
    val image:String,
    val name: String,
    val price: String,
    val itemId: String,
    val description: String,
) : Serializable

fun EMarketResponseItem.toEMarketItem() : EMarketItem{
    return EMarketItem(
        image = image,
        name = name,
        price = price,
        itemId = id,
        description = description
    )
}