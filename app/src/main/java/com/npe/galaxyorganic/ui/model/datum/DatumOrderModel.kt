package com.npe.galaxyorganic.ui.model.datum

import com.google.gson.annotations.SerializedName

data class DatumOrderModel(
    @SerializedName("customers_id")
    val customers_id : String?,
    @SerializedName("total")
    val total : String?,
    @SerializedName("diskon")
    val diskon : String?,
    @SerializedName("grand_total")
    val grand_total : String?,
    @SerializedName("shipping_date")
    val shipping_date : String?,
    @SerializedName("status")
    val status : String?,
    @SerializedName("districs")
    val districs :String?,
    @SerializedName("ordering_user")
    val ordering_user : String?,
    @SerializedName("ordering_email")
    val ordering_email : String?,
    @SerializedName("order_number")
    val order_number : String?,
    @SerializedName("created_at")
    val created_at : String?,
    @SerializedName("id")
    val id : Int?
)