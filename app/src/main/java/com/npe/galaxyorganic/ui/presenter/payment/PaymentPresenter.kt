package com.npe.galaxyorganic.ui.presenter.payment

import android.content.Context
import com.npe.galaxyorganic.ui.model.db.CustomerModel
import com.npe.galaxyorganic.ui.model.db.OrderModel
import com.npe.galaxyorganic.ui.model.db.database
import com.npe.galaxyorganic.ui.view.PaymentView
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class PaymentPresenter : PaymentView.PaymentPresenterView{

    lateinit var view : PaymentView.PaymentUserView

    constructor(view: PaymentView.PaymentUserView) {
        this.view = view
    }

    companion object {
        var dataOrder : MutableList<OrderModel> = mutableListOf()
        var dataCustomer : MutableList<CustomerModel> = mutableListOf()
    }

    override fun showDataOrderSQLDB(context : Context) {
        dataOrder.clear()
        context?.database?.use {
            val result = select(OrderModel.TABLE_ORDER)
            val data = result.parseList(classParser<OrderModel>())
            dataOrder.addAll(data)
            view.dataOrder(dataOrder)
        }
    }

    override fun showDataCustomerSQLDB(context: Context) {
        dataCustomer.clear()
        context?.database?.use {
            val result = select(CustomerModel.TABLE_CUSTOMER)
            val data = result.parseList(classParser<CustomerModel>())
            dataCustomer.addAll(data)
            view.dataCustomer(dataCustomer)
        }
    }
}