package com.emarket.emarketcompose.remoterepositoryimpl.local

import com.emarket.emarketcompose.data.db.EMarketBasketDao
import com.emarket.emarketcompose.data.db.EMarketDb
import com.emarket.emarketcompose.data.repository.local.EMarketBasketRepositoryImpl
import com.emarket.emarketcompose.domain.repository.model.BasketItem
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class EMarketBasketRepositoryImplTest {

    private val mockApi = Mockito.mock(EMarketDb::class.java)
    private val mockBasketDao = Mockito.mock(EMarketBasketDao::class.java)
    private lateinit var mockRepository: EMarketBasketRepositoryImpl

    private val mockEMarketList = listOf(
        BasketItem("1", "image1", 1, "100"),
        BasketItem("2", "image2", 2, "200")
    )

    @Before
    fun setUp() {
        mockRepository = EMarketBasketRepositoryImpl(mockApi)
        Mockito.`when`(mockApi.basketDao()).thenReturn(mockBasketDao)
    }

    @Test
    fun `test getAllBasket`() {
        Mockito.`when`(mockBasketDao.getAllBasket()).thenReturn(mockEMarketList)
        val result = mockRepository.getAllBasket()
        assert(result == mockEMarketList)
    }

    @Test
    fun `test getProductById`(){
        runBlocking {
            Mockito.`when`(mockBasketDao.getProductById("0")).thenReturn(mockEMarketList[0])
            val result = mockRepository.getProductById("0")
            assert(result == mockEMarketList[0])
        }
    }

    @Test
    fun `test addProductInBasket`(){
        runBlocking {
            Mockito.`when`(mockBasketDao.addProductInBasket(mockEMarketList[0])).thenReturn(Unit)
            mockRepository.addProductInBasket(mockEMarketList[0])
        }
    }

     @Test
     fun `test updateProductQuantity`(){
         runBlocking {
             Mockito.`when`(mockBasketDao.updateProductQuantity("0",1)).thenReturn(Unit)
             mockRepository.updateProductQuantity("0",1)
         }
     }

    @Test
    fun `test deleteProductInBasket`(){
        runBlocking {
            Mockito.`when`(mockBasketDao.deleteProductInBasket("0")).thenReturn(Unit)
            mockRepository.deleteProductInBasket("0")
        }
    }

    @Test
    fun `test completeBasket`() {
        runBlocking {
            Mockito.`when`(mockBasketDao.completeBasket()).thenReturn(Unit)
            mockRepository.completeBasket()
        }
    }
}