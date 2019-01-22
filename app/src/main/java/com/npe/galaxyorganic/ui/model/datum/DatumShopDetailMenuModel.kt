package com.npe.galaxyorganic.ui.model.datum

import com.google.gson.annotations.SerializedName

data class DatumShopDetailMenuModel(
    @SerializedName("id")
    val id : Int?,
    @SerializedName("categories_id")
    val categories_id : String?,
    @SerializedName("suppliers_id")
    val suppliers_id : String?,
    @SerializedName("name")
    val name : String?,
    @SerializedName("description")
    val description : String?,
    @SerializedName("weight")
    val weight : String?,
    @SerializedName("buy_price")
    val buy_price : String?,
    @SerializedName("sell_price")
    val sell_price : String?,
    @SerializedName("stock")
    val stock : String?,
    @SerializedName("brands_id")
    val brands_id : Int?,
    @SerializedName("sku")
    val sku : String?,
    @SerializedName("image")
    val image : String?,
    @SerializedName("categories")
    val categorues : String?,
    @SerializedName("brands")
    val brands : String?
)