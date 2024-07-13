package com.emarket.emarketcompose.data.db

import androidx.room.TypeConverter
import com.emarket.emarketcompose.domain.repository.model.FilterItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FilterItemConventers {
    @TypeConverter
    fun fromFilterItem(filterItem: FilterItem?): String? {
        return Gson().toJson(filterItem)
    }

    @TypeConverter
    fun toFilterItem(filterItemString: String?): FilterItem? {
        return Gson().fromJson(filterItemString, object : TypeToken<FilterItem>() {}.type)
    }
}