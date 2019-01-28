package com.npe.galaxyorganic.ui.view

import android.content.Context
import com.npe.galaxyorganic.ui.model.db.CustomerModel
import com.npe.galaxyorganic.ui.model.db.OrderModel
import org.json.JSONArray

interface PaymentView{
    interface PaymentUserView{
        fun dataOrder(dataOrder: MutableList<OrderModel>)
        fun dataCustomer(dataCustomer : MutableList<CustomerModel>)
    }

    interface PaymentPresenterView{
        fun showDataOrderSQLDB(context: Context)
        fun showDataCustomerSQLDB(context: Context)
        fun requestOrder(customer_id : Int,
                         total : Int,
                         discount : Int,
                         grand_total : Int,
                         shipping_date : String,
                         status : Int,
                         districs : Int,
                         ordering_user : String,
                         ordering_detail : JSONArray,
                         ordering_email : String)
    }
}