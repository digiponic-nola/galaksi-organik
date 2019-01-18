package com.npe.galaxyorganic.ui.model

import com.google.gson.annotations.SerializedName

data class RootShopMenuModel(
    @SerializedName("api_status")
    val api_status : Int?,
    @SerializedName("api_message")
    val api_message : String?,
    @SerializedName("api_authorization")
    val api_authorization :String?,
    @SerializedName("data")
    val data : List<DatumShopMenuModel>
)