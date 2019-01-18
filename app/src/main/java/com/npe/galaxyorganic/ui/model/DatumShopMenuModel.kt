package com.npe.galaxyorganic.ui.model

import com.google.gson.annotations.SerializedName

data class DatumShopMenuModel(
    @SerializedName("id")
    val id : Int?,
    @SerializedName("name")
    val name : String?,
    @SerializedName("image")
    val image : String?
)