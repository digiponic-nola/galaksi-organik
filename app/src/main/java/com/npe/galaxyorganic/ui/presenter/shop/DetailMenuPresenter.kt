package com.npe.galaxyorganic.ui.presenter.shop

import android.content.Context
import com.npe.galaxyorganic.ui.model.DatumShopItemModel
import com.npe.galaxyorganic.ui.model.RootShopItemModel
import com.npe.galaxyorganic.ui.model.api.ApiRespository
import com.npe.galaxyorganic.ui.view.ShopView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMenuPresenter(val context: Context, val view: ShopView.DetailMenuView) : ShopView.ShopMenuDetailView {

    var listProduct: ArrayList<DatumShopItemModel> = arrayListOf()
    override fun getProduct(categories_id: Int) {
        val product = ApiRespository.create()
        product.getListProductCategory(categories_id).enqueue(object : Callback<RootShopItemModel> {
            override fun onFailure(call: Call<RootShopItemModel>, t: Throwable) {
                view.failed("Gagal mengambil data")
            }

            override fun onResponse(call: Call<RootShopItemModel>, response: Response<RootShopItemModel>) {
                var dataResponse = response.body()
                if (dataResponse != null) {
                    if (dataResponse.api_message.equals("success")) {
                        listProduct = dataResponse.data as ArrayList<DatumShopItemModel>
                        view.dataItem(listProduct)
                    }
                }
            }

        })
    }


}