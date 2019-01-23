package com.npe.galaxyorganic.ui.model.datum

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DatumLoginModel(
    @Expose
    @SerializedName("id")
    val id : Int?,
    @Expose
    @SerializedName("created_at")
    val created_at : String?,
    @Expose
    @SerializedName("updated_at")
    val updated_at : String?,
    @Expose
    @SerializedName("deleted_at")
    val deleted_at : String?,
    @Expose
    @SerializedName("name")
    val name : String?,
    @Expose
    @SerializedName("email")
    val email : String?,
    @Expose
    @SerializedName("address")
    val address : String?,
    @Expose
    @SerializedName("phone")
    val phone : String?,
    @Expose
    @SerializedName("phone_other")
    val phone_other : String?,
    @Expose
    @SerializedName("states_id")
    val states_id : String?,
    @Expose
    @SerializedName("cities_id")
    val cities_id : String?,
    @Expose
    @SerializedName("districs_id")
    val districs_id : String?
)