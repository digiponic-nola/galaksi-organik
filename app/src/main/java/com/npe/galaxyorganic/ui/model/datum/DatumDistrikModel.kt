package com.npe.galaxyorganic.ui.model.datum

import com.google.gson.annotations.SerializedName

data class DatumDistrikModel(
    @SerializedName("id")
    val id : Int?,
    @SerializedName("name")
    val name : String?
)