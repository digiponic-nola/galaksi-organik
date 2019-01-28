package com.npe.galaxyorganic.ui.model.db

data class CustomerModel(
    val id : Long?,
    val customer_id : Int,
    val customer_name : String,
    val customer_email : String,
    val customer_photo : String,
    val login_from : String
){

    companion object {
        const val TABLE_CUSTOMER = "TABLE_CUSTOMER"
        const val ID = "ID_"
        const val CUSTOMER_ID = "CUSTOMER_ID"
        const val CUSTOMER_NAME = "CUSTOMER_NAME"
        const val CUSTOMER_EMAIL = "CUSTOMER_EMAIL"
        const val CUSTOMER_PHOTO = "CUSTOMER_PHOTO"
        const val LOGIN_FROM = "LOGIN_FROM"
    }
}