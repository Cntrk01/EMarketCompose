package com.emarket.emarketcompose.remoterepositoryimpl.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.emarket.emarketcompose.data.repository.local.EMarketSplashScreenRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

private val APP_ENTRY = booleanPreferencesKey(name = "APP_ENTRY")

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class EMarketSplashScreenRepositoryImplTest {

//    @Mock
//    private lateinit var context: Context
//
//    private lateinit var repository: EMarketSplashScreenRepositoryImpl
//
//    @Before
//    fun setUp() {
//        MockitoAnnotations.initMocks(this)
//        repository = EMarketSplashScreenRepositoryImpl(context)
//    }
//
//    @Test
//    fun `saveAppEntry should store true in DataStore`() {
//        // Run the suspend function using a coroutine
//        runBlocking {
//            repository.saveAppEntry()
//
//            // Verify that the data is stored correctly using Mockito
//            val dataStoreEditor = mock(DataStore::class.java)
//            Mockito.`when`(context.data.edit()).thenReturn(dataStoreEditor)
//
//            val expectedValue = true
//            Mockito.verify(dataStoreEditor).put(APP_ENTRY, expectedValue)
//        }
//    }
//
//    @Test
//    fun `readAppEntry should return true if saved before`() {
//        val testData = mutablePreferencesMapOf(APP_ENTRY to true)
//        val dataStoreFlow = flowOf(testData)
//
//        // Mock the dataStore flow to return the test data
//        Mockito.`when`(context.dataStore.data).thenReturn(dataStoreFlow)
//
//        val result = repository.readAppEntry().first()
//
//        // Assert that the returned value is true
//        assertEquals(true, result)
//    }
//
//    @Test
//    fun `readAppEntry should return false if not saved before`() {
//        val testData = mutablePreferencesMapOf()
//        val dataStoreFlow = flowOf(testData)
//
//        // Mock the dataStore flow to return empty data
//        Mockito.`when`(context.dataStore.data).thenReturn(dataStoreFlow)
//
//        val result = repository.readAppEntry().first()
//
//        // Assert that the returned value is false (default value)
//        assertEquals(false, result)
//    }
}