package com.emarket.emarketcompose.domain.repository.model

import com.emarket.emarketcompose.data.dto.EMarketResponseItem
import java.io.Serializable
import java.util.logging.Filter

data class EMarketItem(
    val image:String,
    val name: String,
    val price: String,
    val itemId: String,
    val description: String,
    val filterItem: FilterItem
) : Serializable

data class FilterItem(
    val model : String,
    val brand : String
)
fun EMarketResponseItem.toFilterItem() : FilterItem{
    return FilterItem(
        model = model,
        brand = brand
    )
}

fun EMarketResponseItem.toEMarketItem() : EMarketItem{
    return EMarketItem(
        image = image,
        name = name,
        price = price,
        itemId = id,
        description = description,
        filterItem = FilterItem(model, brand)
    )
}