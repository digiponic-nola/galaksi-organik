package com.npe.galaxyorganic.ui.view

import android.content.Context
import com.npe.galaxyorganic.ui.model.datum.DatumCitiesModel
import com.npe.galaxyorganic.ui.model.datum.DatumDistrikModel
import com.npe.galaxyorganic.ui.model.db.CustomerModel
import com.npe.galaxyorganic.ui.model.db.OrderModel
import org.json.JSONArray

interface PaymentView{
    interface PaymentUserView{
        fun dataOrder(dataOrder: MutableList<OrderModel>)
        fun dataCustomer(dataCustomer : MutableList<CustomerModel>)
        fun dataDateTime(currentDate: String)
        fun dataKota(listDataCity: ArrayList<DatumCitiesModel>)
        fun dataDistrik(listDataDistrik: ArrayList<DatumDistrikModel>)
        fun dataWaktuKirim(waktu : String)
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
        fun getCurrentDateTime()
        fun getCityApi()
        fun getDistrikApi(states_id : Int)
        fun getWaktuPengiriman(currentTime: String)
    }
}