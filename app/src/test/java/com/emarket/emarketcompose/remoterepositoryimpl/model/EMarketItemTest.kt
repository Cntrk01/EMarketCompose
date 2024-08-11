package com.emarket.emarketcompose.remoterepositoryimpl.model

import com.emarket.emarketcompose.data.dto.EMarketResponseItem
import com.emarket.emarketcompose.domain.repository.model.toEMarketItem
import com.emarket.emarketcompose.domain.repository.model.toFilterItem
import junit.framework.TestCase.assertEquals
import org.junit.Test

class EMarketItemTest {

    @Test
    fun `test toFilterItem`() {

        val responseItem = EMarketResponseItem(
            brand = "brand",
            createdAt = "12.06",
            description = "item_description",
            id = "123",
            image = "image_url",
            model = "model",
            name = "item_name",
            price = "123"
        )

        val filterItem = responseItem.toFilterItem()

        assertEquals("model", filterItem.model)
        assertEquals("brand", filterItem.brand)
    }

    @Test
    fun `test toEMarketItem`() {

        val responseItem = EMarketResponseItem(
            brand = "brand",
            createdAt = "12.06",
            description = "item_description",
            id = "1",
            image = "image_url",
            model = "model",
            name = "item_name",
            price = "123"
        )

        val eMarketItem = responseItem.toEMarketItem()

        assertEquals("image_url", eMarketItem.image)
        assertEquals("item_name", eMarketItem.name)
        assertEquals("123", eMarketItem.price)
        assertEquals("1", eMarketItem.itemId)
        assertEquals("item_description", eMarketItem.description)
        assertEquals("model", eMarketItem.filterItem.model)
        assertEquals("brand", eMarketItem.filterItem.brand)
    }
}