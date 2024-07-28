package com.emarket.emarketcompose.domain.repository.model

import androidx.room.Entity

@Entity(tableName = "Basket",primaryKeys = ["productId"])
data class BasketItem(
    val productId : String,
    val productName : String,
    val productCount : Int
)

fun EMarketItem.toBasketItem () : BasketItem{
    return BasketItem(
        productId = this.itemId,
        productName = this.name,
        productCount = 1
    )
}