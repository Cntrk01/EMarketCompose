package com.emarket.emarketcompose.remoterepositoryimpl.remote

import com.emarket.emarketcompose.data.dto.EMarketResponse
import com.emarket.emarketcompose.data.dto.EMarketResponseItem
import com.emarket.emarketcompose.data.remote.EMarketService
import com.emarket.emarketcompose.data.repository.remote.EMarketRemoteRepositoryImpl
import com.emarket.emarketcompose.domain.repository.model.toEMarketItem
import com.emarket.emarketcompose.domain.repository.model.toFilterItem
import com.emarket.emarketcompose.utils.Response
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.kotlin.whenever
import retrofit2.HttpException

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

    private val fakeEMarketResponse = EMarketResponse().apply { addAll(fakeEMarketResponseItems) }

    @Before
    fun setUp() {
        mockRepository = EMarketRemoteRepositoryImpl(mockApi)
    }

    @Test
    fun `test getData with valid data and filterList`() = runTest {
        val page = 1
        whenever(mockApi.getMarketData(limit = 10, page = page)).thenReturn(fakeEMarketResponse)

        val resultFlow = mockRepository.getData(pageIndex = page)

        resultFlow.collect {
            assert(it is Response.Success)
            assert((it as Response.Success).data == fakeEMarketItems.subList(0, fakeEMarketItems.size))
        }
    }

    @Test
    fun `searchData should return success response when API call is successful`() = runTest {

        val query = "testQuery"
        whenever(mockApi.searchMarketData(query)).thenReturn(fakeEMarketResponse)

        val resultFlow = mockRepository.searchData(query)

        resultFlow.collectLatest {
            assert(it is Response.Success)
            assert((it as Response.Success).data == fakeEMarketItems)

        }
    }

    @Test
    fun `searchData should return error response when API call throws exception`() = runTest {

        val query = "testQuery"
        val exception = HttpException(
            retrofit2.Response.error<Any>(500, ResponseBody.create(null, "Server Error"))
        )
        whenever(mockApi.searchMarketData(query)).thenThrow(exception)

        val resultFlow = mockRepository.searchData(query)

        //Exception için test yazıcam orda kullandığım yapının testini extensions diye bir paket açıp yapcam
        resultFlow.collectLatest {
            assert(it is Response.Error)
            assertEquals(
                "HTTP error: 500 - Server Error",
                (it as Response.Error).message
            )
        }
    }
}