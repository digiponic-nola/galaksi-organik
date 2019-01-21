package com.npe.galaxyorganic.ui.presenter.shop

import android.content.Context
import android.util.Log
import com.npe.galaxyorganic.ui.model.DatumShopDetailMenuModel
import com.npe.galaxyorganic.ui.model.RootShopDetailMenuModel
import com.npe.galaxyorganic.ui.model.api.ApiRespository
import com.npe.galaxyorganic.ui.view.ShopView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMenuPresenter(val context: Context, val view: ShopView.DetailMenuView) : ShopView.ShopMenuDetailView {

    var listProduct: ArrayList<DatumShopDetailMenuModel> = arrayListOf()
    override fun getProduct(categories_id: Int) {
        val product = ApiRespository.create()
        product.getListProductCategory(categories_id).enqueue(object : Callback<RootShopDetailMenuModel> {
            override fun onFailure(call: Call<RootShopDetailMenuModel>, t: Throwable) {
                view.failed("Gagal mengambil data")
            }

            override fun onResponse(call: Call<RootShopDetailMenuModel>, response: Response<RootShopDetailMenuModel>) {
                var dataResponse = response.body()
                if (dataResponse != null) {
                        Log.d("PresenterDetailMenu", "MasukMessage")
                    if (dataResponse.api_message.equals("success")) {
                        listProduct = dataResponse.data as ArrayList<DatumShopDetailMenuModel>
                        view.dataItem(listProduct)
                    }
                }
            }

        })
    }


}