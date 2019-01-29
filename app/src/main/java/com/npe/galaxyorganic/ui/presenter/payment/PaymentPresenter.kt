package com.npe.galaxyorganic.ui.presenter.payment

import android.content.Context
import android.util.Log
import com.npe.galaxyorganic.ui.model.api.ApiRespository
import com.npe.galaxyorganic.ui.model.datum.DatumCitiesModel
import com.npe.galaxyorganic.ui.model.datum.DatumDistrikModel
import com.npe.galaxyorganic.ui.model.db.CustomerModel
import com.npe.galaxyorganic.ui.model.db.OrderModel
import com.npe.galaxyorganic.ui.model.db.database
import com.npe.galaxyorganic.ui.model.root.RequestOrderModel
import com.npe.galaxyorganic.ui.model.root.RootCitiesModel
import com.npe.galaxyorganic.ui.model.root.RootDistrikModel
import com.npe.galaxyorganic.ui.model.root.RootOrderModel
import com.npe.galaxyorganic.ui.view.PaymentView
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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

    override fun getCurrentDateTime() {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val currentDate = sdf.format(Date())
        view.dataDateTime(currentDate)
    }

    override fun getWaktuPengiriman(currentTime: String) {
        val splited = currentTime.split(":")
        val jam = splited.get(0)
        Log.d("JAM", jam.toString())
        if(jam.toInt() >= 12){
            view.dataWaktuKirim("Besok")
        } else if(jam.toInt() < 12){
            view.dataWaktuKirim("Sekarang")
        }
    }

    override fun getCityApi() {
        val city = ApiRespository.create()
        var listDataCity : ArrayList<DatumCitiesModel> = arrayListOf()
        city.getListCities().enqueue(object : Callback<RootCitiesModel>{
            override fun onFailure(call: Call<RootCitiesModel>, t: Throwable) {
                Log.d("GAGAL_CITY_CHECKOUT", t.message)
            }

            override fun onResponse(call: Call<RootCitiesModel>, response: Response<RootCitiesModel>) {
                val data = response.body()
                if(data != null){
                    if(data.api_message.equals("success")){
                        listDataCity = data.data as ArrayList<DatumCitiesModel>
                        view.dataKota(listDataCity)
                    }
                }
            }

        })
    }

    override fun getDistrikApi(states_id: Int) {
        val distrik = ApiRespository.create()
        var listDataDistrik : ArrayList<DatumDistrikModel> = arrayListOf()
        distrik.getListDistrik(states_id).enqueue(object : Callback<RootDistrikModel>{
            override fun onFailure(call: Call<RootDistrikModel>, t: Throwable) {
                Log.d("GAGAL_DISTRIK_CHECKOUT", t.message)
            }

            override fun onResponse(call: Call<RootDistrikModel>, response: Response<RootDistrikModel>) {
                val data = response.body()
                if(data != null){
                    if(data.api_message.equals("success")){
                        listDataDistrik = data.data as ArrayList<DatumDistrikModel>
                        view.dataDistrik(listDataDistrik)
                    }
                }
            }

        })
    }
}