package com.npe.galaxyorganic.ui.model.root

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.npe.galaxyorganic.ui.model.datum.DatumLoginModel

data class RootLoginModel(
    @Expose
    @SerializedName("api_status")
    val api_status : Int?,
    @Expose
    @SerializedName("api_message")
    val api_message : String?,
    @Expose
    @SerializedName("api_authorization")
    val api_authorization : String?,
    @Expose
    @SerializedName("data")
    val data : List<DatumLoginModel>
)