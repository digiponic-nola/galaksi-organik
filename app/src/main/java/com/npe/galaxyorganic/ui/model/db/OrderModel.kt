package com.npe.galaxyorganic.ui.model.db

data class OrderModel(
    val id : Long?,
    val product_id : Int,
    val product_name : String,
    val product_price : Long,
    val quantity : Int,
    val sub_total : Long,
    val buy_quantity : Int
) {
    companion object {
        const val TABLE_ORDER = "TABLE_ORDER"
        const val ID = "ID_"
        const val PRODUCT_ID = "PRODUCT_ID"
        const val PRODUCT_NAME = "PRODUCT_NAME"
        const val PRODUCT_PRICE = "PRODUCT_PRICE"
        const val QUANTITY = "QUANTITY"
        const val SUB_TOTAL= "SUB_TOTAL"
        const val BUY_QUANTITY = "BUY_QUANTITY"
    }
}