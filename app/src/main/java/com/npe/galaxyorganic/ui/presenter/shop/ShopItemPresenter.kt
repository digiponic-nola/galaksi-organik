package com.npe.galaxyorganic.ui.presenter.shop

import android.content.Context
import com.npe.galaxyorganic.ui.model.DatumShopItemModel
import com.npe.galaxyorganic.ui.model.RootShopItemModel
import com.npe.galaxyorganic.ui.model.api.ApiRespository
import com.npe.galaxyorganic.ui.view.ShopView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ShopItemPresenter(val view: ShopView.ShopItemView, val context: Context) : ShopView.ListAllItemView {


    private var listItem = mutableListOf<RootShopItemModel>()
    var checkedItem: Int = 0

    override fun getAllItem() {
        val products = ApiRespository.create()
        var listProducts : ArrayList<DatumShopItemModel> = arrayListOf()
        products.getListProducts().enqueue(object : Callback<RootShopItemModel>{
            override fun onFailure(call: Call<RootShopItemModel>, t: Throwable) {
                view.failedGetProduct()
            }

            override fun onResponse(call: Call<RootShopItemModel>, response: Response<RootShopItemModel>) {
                var dataResponse = response?.body()
                if(dataResponse!=null){
                    if(dataResponse.api_message.equals("success")){
                        listProducts = dataResponse.data as ArrayList<DatumShopItemModel>
                        view.dataItem(listProducts)
                    }
                }
            }

        })
    }

    override fun onDatePickerClicked() {
        var calendar: Calendar = Calendar.getInstance()
        var year: Int = calendar.get(Calendar.YEAR)
        var month: Int = calendar.get(Calendar.MONTH)
        var day: Int = calendar.get(Calendar.DAY_OF_MONTH)
        view.displayDatePickerDialog(year, month, day)
    }

    override fun setDate(year: Int, month: Int, day: Int) {
        var sdf = SimpleDateFormat("dd/MMM/yyyy")
        var calendar: Calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        var dateString: String = sdf.format(calendar.time)
        view.setDateText(dateString)
    }

    override fun onAreaPickerClicked() {
        val items = arrayOf("Malang", "Batu")
        view.displayAreaDialog(items, checkedItem)
    }

    override fun setArea(area: String) {
        view.setAreaText(area)
    }
}


