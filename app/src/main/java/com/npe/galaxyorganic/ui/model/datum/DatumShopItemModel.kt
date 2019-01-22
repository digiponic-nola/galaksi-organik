package com.npe.galaxyorganic.ui.model.datum

import com.google.gson.annotations.SerializedName

data class DatumShopItemModel(
    @SerializedName("id")
    val id : Int?,
    @SerializedName("name")
    val name : String?,
    @SerializedName("description")
    val description : String?,
    @SerializedName("weight")
    val weight : String?,
    @SerializedName("sell_price")
    val sell_price : String?,
    @SerializedName("stock")
    val stock : Int?,
    @SerializedName("image")
    val image : String?,
    @SerializedName("category")
    val category : String?,
    @SerializedName("brands")
    val brands : String?
)