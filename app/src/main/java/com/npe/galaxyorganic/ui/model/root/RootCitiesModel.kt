package com.npe.galaxyorganic.ui.model.root

import com.google.gson.annotations.SerializedName
import com.npe.galaxyorganic.ui.model.datum.DatumCitiesModel

data class RootCitiesModel(
    @SerializedName("api_status")
    val api_status : Int?,
    @SerializedName("api_message")
    val api_message : String?,
    @SerializedName("api_authorization")
    val api_authorization : String?,
    @SerializedName("data")
    val data : List<DatumCitiesModel>
)