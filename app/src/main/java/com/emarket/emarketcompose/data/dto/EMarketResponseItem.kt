package com.emarket.emarketcompose.data.dto

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class EMarketResponseItem(
    @PrimaryKey @SerializedName("id")val id: String,
    @SerializedName("brand") val brand: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("description") val description: String,
    @SerializedName("image") val image: String,
    @SerializedName("model") val model: String,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: String
)