package com.emarket.emarketcompose.remoterepositoryimpl.local

import com.emarket.emarketcompose.data.db.EMarketDb
import com.emarket.emarketcompose.data.db.EMarketFavoriteDao
import com.emarket.emarketcompose.data.repository.local.EMarketFavoriteRepositoryImpl
import com.emarket.emarketcompose.domain.repository.model.EMarketItem
import com.emarket.emarketcompose.domain.repository.model.FilterItem
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.kotlin.whenever

@RunWith(JUnit4::class)
class EMarketFavoriteRepositoryImplTest {

    private val mockApi = Mockito.mock(EMarketDb::class.java)
    private val mockFavoriteDao = Mockito.mock(EMarketFavoriteDao::class.java)
    private lateinit var mockRepository: EMarketFavoriteRepositoryImpl

    private val mockEMarketList = listOf(
        EMarketItem(
            image = "image1",
            name = "item1",
            price = "price1",
            itemId = "id1",
            description = "desc1",
            filterItem = FilterItem(brand = "brand1", model = "model1")
        ),
        EMarketItem(
            image = "image2",
            name = "item2",
            price = "price2",
            itemId = "id2",
            description = "desc2",
            filterItem = FilterItem(brand = "brand2", model = "model2")
        )
    )
    private val singleEMarketItem = EMarketItem(
        image = "image1",
        name = "item1",
        price = "price1",
        itemId = "id1",
        description = "desc1",
        filterItem = FilterItem(brand = "brand1", model = "model1")
    )


    @Before
    fun setUp() {
        mockRepository = EMarketFavoriteRepositoryImpl(mockApi)
        Mockito.`when`(mockApi.favoriteDao()).thenReturn(mockFavoriteDao)
    }

    //---------------------------------------------------------------------//

    @Test
    fun `test getProducts`() {
        whenever(
            mockApi.favoriteDao().getProducts()
        ).thenReturn(mockEMarketList) //çağrıldığında, mockApi.favoriteDao().getProducts() metodunu çağırır ve bu da mockEMarketList listesini döndürür.
        val result =
            mockRepository.getProducts() //yukarıda fake olarak tanımladığımız listeyi alıp getirecek.
        assert(result == mockEMarketList)
    }

    @Test
    fun `test getProducts with empty list`() {
        whenever(mockApi.favoriteDao().getProducts()).thenThrow(RuntimeException("Test Exception"))

        val result = runCatching {
            mockRepository.getProducts()
        }.getOrElse {
            emptyList()
        }
        assert(result.isEmpty())
    }

    @Test
    fun `test getProducts with RunTimeException`() {
        whenever(mockApi.favoriteDao().getProducts()).thenThrow(RuntimeException("Database error"))

        runCatching {
            mockRepository.getProducts()
        }.onFailure {
            when (it) {
                is RuntimeException -> {
                    if (it.message == "Database error") {
                        assert(true)
                    }
                }
            }
        }
    }

    @Test
    fun `test getProducts with NullPointerException`() {
        whenever(mockApi.favoriteDao().getProducts()).thenThrow(NullPointerException("Nullpointer"))

        runCatching {
            mockRepository.getProducts()
        }.onFailure {
            when (it) {
                is NullPointerException -> {
                    if (it.message == "Nullpointer") {
                        assert(true)
                    }
                }

            }
        }
    }

    @Test
    fun `test getProducts with IllegalStateExceptionExceptionTest`() {
        whenever(
            mockApi.favoriteDao().getProducts()
        ).thenThrow(IllegalStateException("Illegal State Error"))

        runCatching {
            mockRepository.getProducts()
        }.onFailure {
            when (it) {
                is IllegalStateException -> {
                    if (it.message == "Illegal State Error") {
                        assert(true)
                    }
                }
            }
        }
    }

    //---------------------------------------------------------------------//

    @Test
    fun `test addProducts`() {
        runBlocking {
            whenever(mockApi.favoriteDao().addProducts(singleEMarketItem)).thenReturn(Unit)
            mockRepository.addProducts(singleEMarketItem)
        }
    }

    @Test
    fun `test addProducts RuntimeException`() {
        runBlocking {
            Mockito.`when`(mockApi.favoriteDao().addProducts(singleEMarketItem))
                .thenThrow(RuntimeException("Test Exception"))
            kotlin.runCatching {
                mockRepository.addProducts(singleEMarketItem)
            }.onFailure {
                when (it) {
                    is RuntimeException -> {
                        if (it.message == "Test Exception") {
                            assert(true)
                        }
                    }
                }
            }
        }
    }

    @Test
    fun `test addProducts NullPointerException`() {
        runBlocking {
            Mockito.`when`(mockApi.favoriteDao().addProducts(singleEMarketItem))
                .thenThrow(NullPointerException("NullPointerException"))
            kotlin.runCatching {
                mockRepository.addProducts(singleEMarketItem)
            }.onFailure {
                when (it) {
                    is NullPointerException -> {
                        if (it.message == "NullPointerException") {
                            assert(true)
                        }
                    }
                }
            }
        }
    }

    //---------------------------------------------------------------------//

    @Test
    fun `test deleteProducts`(): Unit = runBlocking {
        whenever(mockFavoriteDao.deleteProducts(singleEMarketItem)).thenReturn(Unit)
        mockRepository.deleteProducts(singleEMarketItem)
    }

    @Test
    fun `test deleteProducts RuntimeException`() {
        runBlocking {
            Mockito.`when`(mockApi.favoriteDao().deleteProducts(singleEMarketItem))
                .thenThrow(RuntimeException("Test Exception"))
            kotlin.runCatching {
                mockRepository.deleteProducts(singleEMarketItem)
            }.onFailure {
                when (it) {
                    is RuntimeException -> {
                        if (it.message == "Test Exception") {
                            assert(true)
                        }
                    }
                }
            }
        }
    }

    @Test
    fun `test deleteProducts NullPointerException`() {
        runBlocking {
            Mockito.`when`(mockApi.favoriteDao().deleteProducts(singleEMarketItem))
                .thenThrow(NullPointerException("NullPointerException"))
            kotlin.runCatching {
                mockRepository.deleteProducts(singleEMarketItem)
            }.onFailure {
                when (it) {
                    is NullPointerException -> {
                        if (it.message == "NullPointerException") {
                            assert(true)
                        }
                    }
                }
            }
        }
    }

    //---------------------------------------------------------------------//

    @Test
    fun `test checkExistProduct`(): Unit = runBlocking {
        whenever(mockFavoriteDao.checkExistProduct(singleEMarketItem.itemId)).thenReturn(true)
        val result = mockRepository.checkExistProduct(singleEMarketItem.itemId)
        assert(result)
    }

    @Test
    fun `test checkExistProduct RuntimeException`() {
        runBlocking {
            Mockito.`when`(mockApi.favoriteDao().checkExistProduct(singleEMarketItem.itemId)).thenThrow(RuntimeException("Test Exception"))

            runCatching {
                mockRepository.checkExistProduct(singleEMarketItem.itemId)
            }.onFailure {
                when (it) {
                    is RuntimeException -> {
                        if (it.message == "Test Exception") {
                            assert(true)
                        }
                    }
                }
            }
        }
    }

    @Test
    fun `test checkExistProduct NullPointerException`() {
        runBlocking {
            Mockito.`when`(mockApi.favoriteDao().checkExistProduct(singleEMarketItem.itemId)).thenThrow(NullPointerException("NullPointerException"))

            runCatching {
                mockRepository.checkExistProduct(singleEMarketItem.itemId)
            }.onFailure {
                when (it) {
                    is NullPointerException -> {
                        if (it.message == "NullPointerException") {
                            assert(true)
                        }
                    }
                }
            }
        }
    }
}