package com.npe.galaxyorganic.ui.presenter.payment

import android.content.Context
import android.util.Log
import com.npe.galaxyorganic.ui.model.api.ApiRespository
import com.npe.galaxyorganic.ui.model.api.ApiService
import com.npe.galaxyorganic.ui.model.db.CustomerModel
import com.npe.galaxyorganic.ui.model.db.OrderModel
import com.npe.galaxyorganic.ui.model.db.database
import com.npe.galaxyorganic.ui.model.root.RequestOrderModel
import com.npe.galaxyorganic.ui.model.root.RootOrderModel
import com.npe.galaxyorganic.ui.view.PaymentView
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    override fun requestOrder(
        customer_id: Int,
        total: Int,
        discount: Int,
        grand_total: Int,
        shipping_date: String,
        status: Int,
        districs: Int,
        ordering_user: String,
        ordering_detail: JSONArray,
        ordering_email: String
    ) {
        val order = ApiRespository.create()
        val request = RequestOrderModel(customer_id, total, discount, grand_total, shipping_date, status,
            districs,ordering_user, ordering_detail, ordering_email)
        order.sendOrder(request).enqueue(object : Callback<RootOrderModel> {
            override fun onFailure(call: Call<RootOrderModel>, t: Throwable) {
                Log.d("FAILED SEND ORDER", t.message)
            }

            override fun onResponse(call: Call<RootOrderModel>, response: Response<RootOrderModel>) {
                var dataResponse = response.body()
                if(dataResponse != null){
                    if(dataResponse.api_message.equals("success")){

                    }
                }
            }

        })
    }
}