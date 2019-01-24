package com.npe.galaxyorganic.ui.presenter.shop

import android.util.Log
import com.npe.galaxyorganic.ui.model.api.ApiRespository
import com.npe.galaxyorganic.ui.model.datum.DatumCitiesModel
import com.npe.galaxyorganic.ui.model.datum.DatumShopItemModel
import com.npe.galaxyorganic.ui.model.datum.DatumShopMenuModel
import com.npe.galaxyorganic.ui.model.root.RootCitiesModel
import com.npe.galaxyorganic.ui.model.root.RootShopItemModel
import com.npe.galaxyorganic.ui.model.root.RootShopMenuModel
import com.npe.galaxyorganic.ui.view.ShopView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ShopPresenter : ShopView.ListAllItemView {

    var checkedItem: Int = 0
    var listCategory: ArrayList<DatumShopMenuModel> = arrayListOf()
    var listProducts: ArrayList<DatumShopItemModel> = arrayListOf()
    var viewItem: ShopView.ShopItemView

    constructor(viewItem: ShopView.ShopItemView) {
        this.viewItem = viewItem
    }

    override fun getlistCity() {
        val cities = ApiRespository.create()
        var listKota : ArrayList<DatumCitiesModel> = arrayListOf()
        cities.getListCities().enqueue(object : Callback<RootCitiesModel>{
            override fun onFailure(call: Call<RootCitiesModel>, t: Throwable) {
                Log.d("GAGAL_KOTA", t.message)
            }

            override fun onResponse(call: Call<RootCitiesModel>, response: Response<RootCitiesModel>) {
                var data = response.body()
                if(data != null){
                    if(data.api_message.equals("success")){
                        listKota = data.data as ArrayList<DatumCitiesModel>
                        viewItem.alertNamaIdCity(listKota)
                    }
                }
            }

        })
    }

    override fun getDataMenu() {
        val category = ApiRespository.create()
        category.getListCategory().enqueue(object : Callback<RootShopMenuModel> {
            override fun onFailure(call: Call<RootShopMenuModel>, t: Throwable) {
                viewItem.failedMenu("Gagal mengambil data")
            }

            override fun onResponse(call: Call<RootShopMenuModel>, response: Response<RootShopMenuModel>) {
                var dataResponse = response.body()
                if (dataResponse != null) {
                    if (dataResponse.api_message.equals("success")) {
                        listCategory = dataResponse.data as ArrayList<DatumShopMenuModel>
                        viewItem.dataMenu(listCategory)
                    }
                }
            }

        })
    }


    override fun getAllItem() {
        val products = ApiRespository.create()
        products.getListProducts().enqueue(object : Callback<RootShopItemModel> {
            override fun onFailure(call: Call<RootShopItemModel>, t: Throwable) {
                viewItem.failedGetProduct("Product")
            }

            override fun onResponse(call: Call<RootShopItemModel>, response: Response<RootShopItemModel>) {
                var dataResponse = response?.body()
                if (dataResponse != null) {
                    if (dataResponse.api_message.equals("success")) {
                        listProducts = dataResponse.data as ArrayList<DatumShopItemModel>
                        viewItem.dataItem(listProducts)
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
        viewItem.displayDatePickerDialog(year, month, day)
    }

    override fun setDate(year: Int, month: Int, day: Int) {
        var sdf = SimpleDateFormat("dd/MMM/yyyy")
        var calendar: Calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        var dateString: String = sdf.format(calendar.time)
        viewItem.setDateText(dateString)
    }

    override fun onAreaPickerClicked() {
        val cities = ApiRespository.create()
        var items: ArrayList<DatumCitiesModel> = arrayListOf()
        cities.getListCities().enqueue(object : Callback<RootCitiesModel> {
            override fun onFailure(call: Call<RootCitiesModel>, t: Throwable) {
                viewItem.failedGetProduct("Kota")
            }

            override fun onResponse(call: Call<RootCitiesModel>, response: Response<RootCitiesModel>) {
                var dataResponse = response?.body()
                if (dataResponse != null) {
                    if (dataResponse.api_message.equals("success")) {
                        items = dataResponse.data as ArrayList<DatumCitiesModel>
                        getCity(items)
                    }
                }
            }

        })
    }

    private fun getCity(data: ArrayList<DatumCitiesModel>) {
        val namesArr = arrayOfNulls<String>(data.size)
        for (i in data.indices) {
            namesArr[i] = data.get(i).name
        }
        viewItem.displayAreaDialog(namesArr, checkedItem)
    }

    override fun setArea(area: String) {
        viewItem.setAreaText(area)
    }
}


