package com.npe.galaxyorganic.ui.model.datum

import com.google.gson.annotations.SerializedName

data class DatumCitiesModel(
    @SerializedName("id")
    val id : Int?,
    @SerializedName("states_id")
    val states_id : Int?,
    @SerializedName("name")
    val name : String?
)