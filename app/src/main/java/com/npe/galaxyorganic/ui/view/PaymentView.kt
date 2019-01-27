package com.npe.galaxyorganic.ui.view

import android.content.Context
import com.npe.galaxyorganic.ui.model.db.CustomerModel
import com.npe.galaxyorganic.ui.model.db.OrderModel

interface PaymentView{
    interface PaymentUserView{
        fun dataOrder(dataOrder: MutableList<OrderModel>)
        fun dataCustomer(dataCustomer : MutableList<CustomerModel>)
    }

    interface PaymentPresenterView{
        fun showDataOrderSQLDB(context: Context)
        fun showDataCustomerSQLDB(context: Context)
    }
}