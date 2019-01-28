package com.npe.galaxyorganic.ui.model.root

import org.json.JSONArray

data class RequestOrderModel(
    val customer_id : Int,
    val total : Int,
    val discount : Int,
    val grand_total : Int,
    val shipping_date : String,
    val status : Int?,
    val districs : Int?,
    val ordering_user : String,
    val order_detail : JSONArray,
    val ordering_email : String
)