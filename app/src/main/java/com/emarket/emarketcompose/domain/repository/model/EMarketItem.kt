package com.emarket.emarketcompose.domain.repository.model

import com.emarket.emarketcompose.data.dto.EMarketResponseItem

data class EMarketItem(
    val image:String,
    val name: String,
    val price: String,
    val itemId: String,
    val description: String,
)

fun EMarketResponseItem.toEMarketItem() : EMarketItem{
    return EMarketItem(
        image = image,
        name = name,
        price = price,
        itemId = id,
        description = description
    )
}