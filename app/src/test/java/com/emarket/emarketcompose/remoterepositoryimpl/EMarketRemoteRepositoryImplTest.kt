package com.emarket.emarketcompose.remoterepositoryimpl

import com.emarket.emarketcompose.data.dto.EMarketResponse
import com.emarket.emarketcompose.data.dto.EMarketResponseItem
import com.emarket.emarketcompose.data.remote.EMarketService
import com.emarket.emarketcompose.data.repository.remote.EMarketRemoteRepositoryImpl
import com.emarket.emarketcompose.domain.repository.model.toEMarketItem
import com.emarket.emarketcompose.domain.repository.model.toFilterItem
import com.emarket.emarketcompose.utils.Response
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.kotlin.whenever

@RunWith(JUnit4::class)
class EMarketRemoteRepositoryImplTest {

    private lateinit var mockRepository: EMarketRemoteRepositoryImpl
    private val mockApi = Mockito.mock(EMarketService::class.java)

    private val fakeEMarketResponseItems = listOf(
        EMarketResponseItem("image1", "item1", "price1", "id1", "desc1", "model1", "brand1", "1200"),
        EMarketResponseItem("image2", "item2", "price2", "id2", "desc2", "model2", "brand2", "100"),
        EMarketResponseItem("image3", "item3", "price3", "id3", "desc3", "model3", "brand3", "200")
    )

    private val fakeEMarketItems = fakeEMarketResponseItems.map { it.toEMarketItem() }

    private val fakeFilterItems = fakeEMarketResponseItems.map { it.toFilterItem() }

    private val fakeEMarketResponse = EMarketResponse().apply { addAll(fakeEMarketResponseItems) }

    @Before
    fun setUp() {
        mockRepository = EMarketRemoteRepositoryImpl(mockApi)
    }

    @Test
    fun `test getData with valid data and filterList`() = runTest {
        val totalPageItem = 0

        // Mock the remoteApi to return our fake data
        whenever(mockApi.getMarketData()).thenReturn(fakeEMarketResponse)

        // Call getData and collect the results
        val resultFlow = mockRepository.getData(totalPageItem,
            { size ->
                assert(size == fakeEMarketItems.size)
            }, { filters ->
                assert(filters == fakeFilterItems)
            })

        resultFlow.collect {
            assert(it is Response.Success)
            assert((it as Response.Success).data == fakeEMarketItems.subList(0, fakeEMarketItems.size))
        }
    }

    @Test
    fun `test getData with no more data available`() = runTest {
        val totalPageItem =
            fakeEMarketItems.size // Bu durumda, toplam sayfa öğesi mevcut veri sayısına eşit

        // Mock the remoteApi to return our fake data
        whenever(mockApi.getMarketData()).thenReturn(fakeEMarketResponse)

        // Call getData and collect the results
        val resultFlow = mockRepository.getData(totalPageItem, { _ -> }, { _ -> })
        // val result = resultFlow.first() yapmıştım. Burada şöyle birşey oluştu akış bitip blok kapandığında en son bu kontrol ettiğim erroru atıyor ve exception yiyoruz.
        //Flow was aborted, no more elements needed, but then emission attempt of value 'com.emarket.emarketcompose.utils.Response$Error@1698d7c0' has been detected.
        //    Emissions from 'catch' blocks are prohibited in order to avoid unspecified behaviour, 'Flow.catch' operator can be used instead.
        //    For a more detailed explanation, please refer to Flow documentation. bundan dolayı bizim değerleri burada toplamamız gerekli.
        resultFlow.collect {
            assert(it is Response.Error)
            assert((it as Response.Error).message == "No more data available")
        }
    }

    @Test
    fun `test getData with exception`() = runTest {
        val totalPageItem = 0

        whenever(mockApi.getMarketData()).thenThrow(RuntimeException("API error"))

        val resultFlow = mockRepository.getData(totalPageItem, { _ -> }, { _ -> })

        val result = resultFlow.first()

        assert(result is Response.Error)
        assert((result as Response.Error).message == "Failed to fetch data: API error")
    }
}