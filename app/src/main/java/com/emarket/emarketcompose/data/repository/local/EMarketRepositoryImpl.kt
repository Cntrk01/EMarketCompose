package com.emarket.emarketcompose.data.repository.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.emarket.emarketcompose.domain.repository.local.EMarketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EMarketRepositoryImpl(
    private val context: Context
) : EMarketRepository {
    override suspend fun saveAppEntry() {
        context.dataStore.edit {settings->
            settings[APP_ENTRY] = true
        }
    }

    override fun readAppEntry(): Flow<Boolean> {
        return context.dataStore.data.map {prefences->
            prefences[APP_ENTRY] ?: false
        }
    }
}

private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "APP ENTRY")

private val APP_ENTRY= booleanPreferencesKey(name = "APP ENTRY")