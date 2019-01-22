package com.npe.galaxyorganic.ui.presenter.shop

import android.content.Context
import com.npe.galaxyorganic.ui.model.datum.DatumShopMenuModel
import com.npe.galaxyorganic.ui.model.root.RootShopMenuModel
import com.npe.galaxyorganic.ui.model.api.ApiRespository
import com.npe.galaxyorganic.ui.view.ShopView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShopMenuPresenter(private val view: ShopView.ShopMenuView, private val context: Context) :
    ShopView.ShopMenuPresenterView {
    
    override fun getDataMenu() {
        val category = ApiRespository.create()
        var listCategory: ArrayList<DatumShopMenuModel> = arrayListOf()
        category.getListCategory().enqueue(object : Callback<RootShopMenuModel> {
            override fun onFailure(call: Call<RootShopMenuModel>, t: Throwable) {
                view.failedMenu("Gagal mengambil data")
            }

            override fun onResponse(call: Call<RootShopMenuModel>, response: Response<RootShopMenuModel>) {
                var dataResponse = response.body()
                if (dataResponse != null) {
                    if (dataResponse.api_message.equals("success")) {
                        listCategory = dataResponse.data as ArrayList<DatumShopMenuModel>
                        view.dataMenu(listCategory)
                    }
                }
            }

        })
    }

}