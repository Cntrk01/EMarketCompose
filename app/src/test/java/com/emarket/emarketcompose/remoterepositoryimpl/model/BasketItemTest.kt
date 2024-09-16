package com.emarket.emarketcompose.remoterepositoryimpl.model

import com.emarket.emarketcompose.domain.repository.model.BasketItem
import com.emarket.emarketcompose.domain.repository.model.EMarketItem
import com.emarket.emarketcompose.domain.repository.model.FilterItem
import com.emarket.emarketcompose.domain.repository.model.toBasketItem
import junit.framework.TestCase.assertEquals
import org.junit.Test

class BasketItemTest {

    @Test
    fun `test toBasketItem`() {
        val basketItem1 = BasketItem("1", "item1", 1, "10.0")

        val emarketItem = EMarketItem(
            image = "image1",
            name = "item1",
            price = "10.0",
            itemId = "1",
            description = "desc1",
            filterItem = FilterItem(brand = "brand1", model = "model1")
        )

        val x =emarketItem.toBasketItem()

        assertEquals(basketItem1, x)
    }
}